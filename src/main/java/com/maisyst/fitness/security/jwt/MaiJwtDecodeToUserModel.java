package com.maisyst.fitness.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.maisyst.fitness.models.UserModel;
import org.springframework.stereotype.Component;

import static com.maisyst.fitness.utils.MaiUtils.stringToAuthRole;

/**
 * @author MaiSYST
 * Convert  and extract token-decoded data to UserModel
 */
@Component
public class MaiJwtDecodeToUserModel {
    /**
     *
     * @param decodedJWT decode provide to MaiJwtDecoder
     * @return model user with: <br>user_id<br>username<br>role<br>isActive
     */
    public UserModel convertNow(DecodedJWT decodedJWT) {
        return new UserModel(
                Integer.parseInt(decodedJWT.getSubject()),
                String.valueOf(decodedJWT.getClaim("username")),
                stringToAuthRole(String.valueOf(decodedJWT.getClaim("role"))),
                Boolean.getBoolean(String.valueOf(decodedJWT.getClaim("isAccountActivate")))
        );
    }
}
