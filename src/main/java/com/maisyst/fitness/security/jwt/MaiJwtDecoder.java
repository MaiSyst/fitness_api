package com.maisyst.fitness.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.maisyst.fitness.security.env.MaiJWTProperties;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(MaiJwtDecoder.class.getName()).log(Level.SEVERE,ex.getMessage());
        }
        return decodedJWT;
    }

}
