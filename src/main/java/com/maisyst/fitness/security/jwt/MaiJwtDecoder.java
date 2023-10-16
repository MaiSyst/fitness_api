package com.maisyst.fitness.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.maisyst.fitness.security.env.MaiJWTProperties;
import org.springframework.stereotype.Component;

@Component
public class MaiJwtDecoder {
    private final MaiJWTProperties maiJWTProperties;

    public MaiJwtDecoder(MaiJWTProperties maiJWTProperties) {
        this.maiJWTProperties = maiJWTProperties;
    }

    public DecodedJWT decodedJWT(String token) {
        DecodedJWT decodedJWT = null;
        try {
            JWTVerifier jwtVerifier=JWT
                .require(Algorithm.HMAC256(maiJWTProperties.getSecretToken()))
                .build();
            decodedJWT=jwtVerifier.verify(token);
        }catch (JWTVerificationException ex){
            System.out.println(ex.getMessage());
        }
        return decodedJWT;
    }
}
