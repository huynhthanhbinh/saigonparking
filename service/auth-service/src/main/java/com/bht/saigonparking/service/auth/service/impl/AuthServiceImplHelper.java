package com.bht.saigonparking.service.auth.service.impl;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.service.auth.base.BaseBean;
import com.google.common.io.ByteStreams;

import lombok.AccessLevel;
import lombok.Getter;

/**
 *
 * @author bht
 */
@Component
public final class AuthServiceImplHelper implements BaseBean {

    @Value("${token.user-id.decode.key}")
    private Long userIdEncodeKey;

    @Value("${token.rsa-private-key.path}")
    private Resource rsaPrivateKeyResource;

    @Getter(AccessLevel.PACKAGE)
    private String rsaPrivateKeyContent;

    @Override
    public void initialize() throws IOException {
        BaseBean.super.initialize();
        rsaPrivateKeyContent = getPrivateKeyContent();
    }

    String generateAccessToken(@NotNull Long userId,
                               @NotNull UserRole userRole) {
        return "tempAccessToken";
    }

    private String getPrivateKeyContent() throws IOException {
        return new String(ByteStreams.toByteArray(rsaPrivateKeyResource.getInputStream()))
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\n", "");
    }
}