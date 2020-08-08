package com.bht.saigonparking.common.base;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;
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
@RequiredArgsConstructor
public abstract class BaseWebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor implements BaseBean {

    private final SaigonParkingAuthentication authentication;

    protected abstract String getAccessTokenFromHttpRequest(@NonNull ServerHttpRequest httpRequest);
    
    protected abstract void postAuthentication(@NonNull SaigonParkingTokenBody saigonParkingTokenBody,
                                               @NonNull Map<String, Object> webSocketSessionAttributes);

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest httpRequest,
                                   @NonNull ServerHttpResponse httpResponse,
                                   @NonNull WebSocketHandler webSocketHandler,
                                   @NonNull Map<String, Object> attributes) throws Exception {
        try {
            String accessToken = getAccessTokenFromHttpRequest(httpRequest);
            SaigonParkingTokenBody saigonParkingTokenBody = authentication.parseJwtToken(accessToken);
            postAuthentication(saigonParkingTokenBody, attributes);

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
}