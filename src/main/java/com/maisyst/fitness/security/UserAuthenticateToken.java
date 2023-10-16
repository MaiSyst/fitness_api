package com.maisyst.fitness.security;

import com.maisyst.fitness.models.UserModel;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserAuthenticateToken extends AbstractAuthenticationToken {
    private final UserModel userModel;

    public UserAuthenticateToken(UserModel userModel) {
        super(userModel.getAuthorities());
        this.userModel = userModel;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserModel getPrincipal() {
        return userModel;
    }
}
