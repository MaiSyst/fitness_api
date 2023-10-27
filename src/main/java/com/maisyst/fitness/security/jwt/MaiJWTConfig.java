package com.maisyst.fitness.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.maisyst.fitness.models.AuthRole;
import com.maisyst.fitness.security.env.MaiJWTProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * @author MaiSYST
 */
@Component
public class MaiJWTConfig {
    private final MaiJWTProperties maiJWTProperties;

    public MaiJWTConfig(MaiJWTProperties maiJWTProperties) {
        this.maiJWTProperties = maiJWTProperties;
    }

    /**
     * Created the new token for each user sign-in
     * @param user_id is user identify in database
     * @param username username in database
     * @param role function occupied by the user in the database
     * @param isAccountActive is the status of the user account
     * @return token created with information user
     */
    public String createToken(UUID user_id, String username, AuthRole role, boolean isAccountActive, int validTokenInDays){
        return JWT.create()
                .withSubject(String.valueOf(user_id))
                .withExpiresAt(Instant.now().plus(Duration.of(validTokenInDays, ChronoUnit.DAYS)))
                .withClaim("username",username)
                .withClaim("role",role.name())
                .withClaim("isAccountActivate",isAccountActive)
                .sign(Algorithm.HMAC256(maiJWTProperties.getSecretToken()));
    }
}
