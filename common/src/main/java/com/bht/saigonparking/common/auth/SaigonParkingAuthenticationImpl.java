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
import java.util.Random;

import org.springframework.data.util.Pair;

import com.google.common.io.ByteStreams;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 *
 * @author bht
 */
public final class SaigonParkingAuthenticationImpl implements SaigonParkingAuthentication {

    private static final String SAIGON_PARKING_ISSUER = "www.saigonparking.wtf";
    private static final String USER_ROLE_KEY_NAME = "role";
    private static final String FACTOR_KEY_NAME = "fac";
    private static final short MAX_RANDOM_EXCLUSIVE = 1000;

    private Long userIdDecodeKey;
    private Key secretKey;

    public SaigonParkingAuthenticationImpl() throws IOException {
        init();
    }

    private Long encryptUserId(Long userId, Integer factor) {
        return userId ^ (userIdDecodeKey * factor);
    }

    private Long decryptUserId(Long encodedUserId, Integer factor) {
        return encodedUserId ^ (userIdDecodeKey * factor);
    }

    private void init() throws IOException {
        Properties properties = new Properties();
        properties.load(Objects.requireNonNull(SaigonParkingAuthenticationImpl.class
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
                        .requireNonNull(SaigonParkingAuthenticationImpl.class
                                .getClassLoader()
                                .getResourceAsStream(keyPath))))
                        .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                        .replace("-----END RSA PRIVATE KEY-----", "")
                        .replaceAll("\\n", "")
                        .getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateJwtToken(Long userId, String userRole, Integer timeAmount, ChronoUnit timeUnit) {
        Instant now = Instant.now();
        Integer factor = new Random().nextInt(MAX_RANDOM_EXCLUSIVE);
        return Jwts.builder()
                .setIssuer(SAIGON_PARKING_ISSUER)
                .claim(USER_ROLE_KEY_NAME, userRole)
                .claim(FACTOR_KEY_NAME, factor)
                .setSubject(encryptUserId(userId, factor).toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(timeAmount, timeUnit)))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Pair<Long, String> parseJwtToken(String jwtBearerToken) {
        String realToken = jwtBearerToken.replace("Bearer ", "");
        Claims tokenBody = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(realToken)
                .getBody();

        Long userId = decryptUserId(Long.valueOf(tokenBody.getSubject()), tokenBody.get(FACTOR_KEY_NAME, Integer.class));
        String userRole = tokenBody.get(USER_ROLE_KEY_NAME, String.class);
        return Pair.of(userId, userRole);
    }
}