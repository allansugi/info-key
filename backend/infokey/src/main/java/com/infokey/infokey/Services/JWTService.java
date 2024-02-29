package com.infokey.infokey.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTService {
    private final String secret = "secret";
    private final String accessClaim = "id";
    private final int timeExpiry = 3600;
    private final String issuer = "auth0";

    /**
     * uses HMAC512 algorithm which is supported by auth0 JWT
     * for more information, visit https://github.com/auth0/java-jwt
     * @param id userId
     * @return new token
     */
    public String createToken(String id) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create()
                    .withIssuer("auth0")
                    .withClaim(accessClaim, id)
                    .sign(algorithm);
    }

    /**
     * Important: expiry for valid token is for 1h
     * @param token from header
     * @return decoded token (userId)
     */
    public String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                                    .acceptLeeway(timeExpiry)
                                    .withIssuer(issuer)
                                    .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(accessClaim).asString();
    }
}
