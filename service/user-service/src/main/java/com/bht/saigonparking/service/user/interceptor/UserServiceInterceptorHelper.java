package com.bht.saigonparking.service.user.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.service.user.base.BaseBean;
import com.google.common.io.ByteStreams;

import javassist.NotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author bht
 */
@Component
public final class UserServiceInterceptorHelper implements BaseBean {

    @Qualifier("localhost")
    @Setter(onMethod = @__(@Autowired))
    private String localHost;

    @Value("${token.rsa-private-key.path}")
    private Resource rsaPrivateKeyResource;

    @Getter(AccessLevel.PACKAGE)
    private String rsaPrivateKeyContent;

    @Override
    public void initialize() throws NotFoundException, IOException {
        BaseBean.super.initialize();
        rsaPrivateKeyContent = getPrivateKeyContent();
    }

    boolean isOnSameNetwork(String otherAddress) {
        return otherAddress.substring(0, otherAddress.lastIndexOf('.'))
                .equals(localHost.substring(0, localHost.lastIndexOf('.')));
    }

    private String getPrivateKeyContent() throws IOException {
        return new String(ByteStreams.toByteArray(rsaPrivateKeyResource.getInputStream()))
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\n", "");
    }
}