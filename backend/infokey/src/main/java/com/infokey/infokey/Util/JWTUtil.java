package com.infokey.infokey.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private static final String SECRET = "secret";
    private static final String ACCESS_CLAIM = "id";
    private static final int TIME_EXPIRY = 3600;
    private static final String ISSUER = "auth0";

    public JWTUtil() {

    }

    /**
     * uses HMAC512 algorithm which is supported by auth0 JWT
     * for more information, visit <a href="https://github.com/auth0/java-jwt">...</a>
     * @param id userId
     * @return new token
     */
    public String createToken(String id) {
        Algorithm algorithm = Algorithm.HMAC512(SECRET);
        return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(ACCESS_CLAIM, id)
                    .sign(algorithm);
    }

    /**
     * token only valid for 1 hour
     * @param token from header
     * @return decoded token (userId) or null
     */
    public String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                                    .acceptLeeway(TIME_EXPIRY)
                                    .withIssuer(ISSUER)
                                    .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(ACCESS_CLAIM).asString();
    }
}
