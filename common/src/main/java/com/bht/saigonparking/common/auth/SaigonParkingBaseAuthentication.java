package com.bht.saigonparking.common.auth;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import com.google.common.io.ByteStreams;

/**
 *
 * authenticate JWT token
 *
 * @author bht
 */
public final class SaigonParkingBaseAuthentication {

    private Long userIdDecodeKey;
    private String privateKeyContent;

    public static final String AUTHORIZATION_KEY_NAME = "authorization";
    public static final String INTERNAL_KEY_NAME = "saigon-parking-internal";

    public SaigonParkingBaseAuthentication() throws IOException {
        init();
    }

    public Long encodeUserId(Long userId) {
        return userId ^ userIdDecodeKey;
    }

    public Long decodeUserId(Long encodedUserId) {
        return encodedUserId ^ userIdDecodeKey;
    }

    private void init() throws IOException {
        Properties properties = new Properties();
        properties.load(Objects.requireNonNull(SaigonParkingBaseAuthentication.class
                .getClassLoader()
                .getResourceAsStream("common.properties")));

        userIdDecodeKey = Long.valueOf((String) properties.get("token.user-id.decode.key"));
        privateKeyContent = getPrivateKeyContent((String) properties.get("token.rsa-private-key.path"));
    }

    private String getPrivateKeyContent(String keyPath) throws IOException {
        return new String(ByteStreams.toByteArray(Objects
                .requireNonNull(SaigonParkingBaseAuthentication.class
                        .getClassLoader()
                        .getResourceAsStream(keyPath))))
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\n", "");
    }
}