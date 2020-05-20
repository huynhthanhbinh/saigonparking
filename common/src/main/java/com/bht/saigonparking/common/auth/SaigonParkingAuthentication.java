package com.bht.saigonparking.common.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import com.google.common.io.ByteStreams;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 *
 * authenticate JWT token
 *
 * @author bht
 */
public final class SaigonParkingAuthentication {

    private Long userIdDecodeKey;
    private Key secretKey;

    public static final String SAIGON_PARKING_ISSUER = "www.saigonparking.wtf";
    public static final String AUTHORIZATION_KEY_NAME = "authorization";
    public static final String USER_ROLE_KEY_NAME = "role";
    public static final String INTERNAL_KEY_NAME = "saigon-parking-internal";

    public SaigonParkingAuthentication() throws IOException {
        init();
    }

    private Long encodeUserId(Long userId) {
        return userId ^ userIdDecodeKey;
    }

    private Long decodeUserId(Long encodedUserId) {
        return encodedUserId ^ userIdDecodeKey;
    }

    private void init() throws IOException {
        Properties properties = new Properties();
        properties.load(Objects.requireNonNull(SaigonParkingAuthentication.class
                .getClassLoader()
                .getResourceAsStream("common.properties")));

        userIdDecodeKey = Long.valueOf((String) properties.get("token.user-id.decode.key"));
        secretKey = getSecretKey((String) properties.get("token.rsa-private-key.path"));
    }

    private Key getSecretKey(String keyPath) throws IOException {
        return Keys.hmacShaKeyFor(getSecretKeyByteArray(keyPath));
    }

    private byte[] getSecretKeyByteArray(String keyPath) throws IOException {
        return Base64.getDecoder()
                .decode(new String(ByteStreams.toByteArray(Objects
                        .requireNonNull(SaigonParkingAuthentication.class
                                .getClassLoader()
                                .getResourceAsStream(keyPath))))
                        .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                        .replace("-----END RSA PRIVATE KEY-----", "")
                        .replaceAll("\\n", "")
                        .getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(Long userId, String userRole) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuer(SAIGON_PARKING_ISSUER)
                .claim(USER_ROLE_KEY_NAME, userRole)
                .setSubject(encodeUserId(userId).toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();
    }

    public SaigonParkingTokenBody parseJwtToken(String jwtBearerToken) {
        String realToken = jwtBearerToken.replace("Bearer ", "");
        Claims tokenBody = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(realToken)
                .getBody();

        return SaigonParkingTokenBody.builder()
                .userId(decodeUserId(Long.valueOf(tokenBody.getSubject())))
                .userRole(tokenBody.get(USER_ROLE_KEY_NAME, String.class))
                .build();
    }
}