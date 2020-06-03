package com.bht.saigonparking.common.auth;

import static com.bht.saigonparking.common.auth.SaigonParkingTokenType.*;

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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;

import com.bht.saigonparking.common.util.LoggingUtil;
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
    private static final short MAX_RANDOM_EXCLUSIVE = 1000;

    private static final String USER_ROLE_KEY_NAME = "role";
    private static final String FACTOR_KEY_NAME = "fac";
    private static final String TOKEN_TYPE_KEY_NAME = "classification";

    private Long userIdDecodeKey;
    private Key secretKey;

    public SaigonParkingAuthenticationImpl() {
        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(SaigonParkingAuthenticationImpl.class
                    .getClassLoader()
                    .getResourceAsStream("common.properties")));

            userIdDecodeKey = Long.valueOf((String) properties.get("token.user-id.decode.key"));
            secretKey = getSecretKey((String) properties.get("token.rsa-private-key.path"));

        } catch (IOException e) {
            LoggingUtil.log(Level.ERROR, "AUTH", "Read resource", "Resource is unavailable !");
        }
    }

    private Long encryptUserId(@NotNull Long userId, @NotNull Integer factor) {
        return userId ^ (userIdDecodeKey * factor);
    }

    private Long decryptUserId(@NotNull Long encodedUserId, @NotNull Integer factor) {
        return encodedUserId ^ (userIdDecodeKey * factor);
    }

    private Key getSecretKey(@NotEmpty String keyPath) throws IOException {
        return Keys.hmacShaKeyFor(getSecretKeyByteArray(keyPath));
    }

    private byte[] getSecretKeyByteArray(@NotEmpty String keyPath) throws IOException {
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

    private String generateJwtToken(@NotNull SaigonParkingTokenType type,
                                    @NotNull Long userId,
                                    @NotEmpty String userRole,
                                    @NotNull Integer timeAmount,
                                    @NotNull ChronoUnit timeUnit) {

        Instant now = Instant.now();
        Integer factor = new Random().nextInt(MAX_RANDOM_EXCLUSIVE);
        Long encryptedUserId = encryptUserId(userId, factor);

        return Jwts.builder()
                .setId(String.format("%d@%d", encryptedUserId, now.toEpochMilli()))
                .setIssuer(SAIGON_PARKING_ISSUER)
                .claim(USER_ROLE_KEY_NAME, userRole)
                .claim(FACTOR_KEY_NAME, factor)
                .claim(TOKEN_TYPE_KEY_NAME, type)
                .setSubject(encryptedUserId.toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(timeAmount, timeUnit)))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public SaigonParkingTokenBody parseJwtToken(@NotEmpty String jsonWebToken) {
        String realToken = jsonWebToken.replace("Bearer ", "");
        Claims tokenBody = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(realToken)
                .getBody();

        return SaigonParkingTokenBody.builder()
                .tokenId(tokenBody.getId())
                .tokenType(SaigonParkingTokenType.valueOf(tokenBody.get(TOKEN_TYPE_KEY_NAME, String.class)))
                .userId(decryptUserId(Long.valueOf(tokenBody.getSubject()), tokenBody.get(FACTOR_KEY_NAME, Integer.class)))
                .userRole(tokenBody.get(USER_ROLE_KEY_NAME, String.class))
                .build();
    }

    @Override
    public String generateAccessToken(@NotNull Long userId, @NotEmpty String userRole) {
        return generateJwtToken(ACCESS_TOKEN, userId, userRole, 30, ChronoUnit.MINUTES);
    }

    @Override
    public String generateRefreshToken(@NotNull Long userId, @NotEmpty String userRole) {
        return generateJwtToken(REFRESH_TOKEN, userId, userRole, 30, ChronoUnit.DAYS);
    }

    @Override
    public String generateActivateAccountToken(@NotNull Long userId, @NotEmpty String userRole) {
        return generateJwtToken(ACTIVATE_TOKEN, userId, userRole, 5, ChronoUnit.MINUTES);
    }

    @Override
    public String generateResetPasswordToken(@NotNull Long userId, @NotEmpty String userRole) {
        return generateJwtToken(RESET_PW_TOKEN, userId, userRole, 5, ChronoUnit.MINUTES);
    }
}