package com.maisyst.fitness.security.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author MaiSYST
 * Give to application properties(file application.properties) for hiding my token secret key
 */
@Configuration
@ConfigurationProperties("mai.jwt")
public class MaiJWTProperties {
    private String secretToken;

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }
}
