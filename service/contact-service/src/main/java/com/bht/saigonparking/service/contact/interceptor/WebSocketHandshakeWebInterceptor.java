package com.bht.saigonparking.service.contact.interceptor;

import java.net.URI;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.exception.MissingTokenException;
import com.bht.saigonparking.common.util.LoggingUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public final class WebSocketHandshakeWebInterceptor extends HttpSessionHandshakeInterceptor {

    public static final String SAIGON_PARKING_USER_KEY = "saigon_parking_user";
    private static final String AUTH_PATH_PREFIX = "web?token=";
    private static final Short AUTH_PATH_PREFIX_LENGTH = 10;

    private final SaigonParkingAuthentication authentication;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest httpRequest,
                                   @NonNull ServerHttpResponse httpResponse,
                                   @NonNull WebSocketHandler webSocketHandler,
                                   @NonNull Map<String, Object> attributes) throws Exception {
        try {
            String accessToken = getAccessTokenFromUri(httpRequest.getURI());
            if (accessToken.isEmpty()) {
                throw new MissingTokenException();
            }

            Long userId = authentication.parseJwtToken(accessToken).getUserId();
            attributes.put(SAIGON_PARKING_USER_KEY, userId);

        } catch (ExpiredJwtException expiredJwtException) {
            LoggingUtil.log(Level.ERROR, "WebSocketInterceptor", "Exception", "ExpiredJwtException");
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;

        } catch (SignatureException signatureException) {
            LoggingUtil.log(Level.ERROR, "WebSocketInterceptor", "Exception", "SignatureException");
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;

        } catch (MalformedJwtException malformedJwtException) {
            LoggingUtil.log(Level.ERROR, "WebSocketInterceptor", "Exception", "MalformedJwtException");
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;

        } catch (DecodingException decodingException) {
            LoggingUtil.log(Level.ERROR, "WebSocketInterceptor", "Exception", "DecodingException");
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;

        } catch (MissingTokenException missingTokenException) {
            LoggingUtil.log(Level.ERROR, "WebSocketInterceptor", "Exception", "MissingTokenException");
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;

        } catch (Exception exception) {
            LoggingUtil.log(Level.ERROR, "WebSocketInterceptor", "Exception", exception.getClass().getSimpleName());
            httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return false;
        }
        return super.beforeHandshake(httpRequest, httpResponse, webSocketHandler, attributes);
    }

    private String getAccessTokenFromUri(URI uriWithAccessToken) {
        String uriString = uriWithAccessToken.toString();
        return uriString.substring(uriString.lastIndexOf(AUTH_PATH_PREFIX) + AUTH_PATH_PREFIX_LENGTH);
    }
}