package com.maisyst.fitness.security.jwt;

import com.maisyst.fitness.security.UserAuthenticateToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * @author MaiSYST
 * Filter all request and response API
 * if user isn't authenticate blocked access otherwise forwards user
 * properties are injected
 * The property MaiJwtDecode decode token
 * property MaiJwtDecodeToUserModel takes the decoded token and converts it to UserModel.
 * The next step UserAuthenticateToken class creates user authentication to give access.
 */
@Component
public class MaiRequestAuthFilter extends OncePerRequestFilter {
    final private MaiJwtDecoder maiJwtDecode;
    final private MaiJwtDecodeToUserModel maiJwtDecodeToUserModel;

    public MaiRequestAuthFilter(MaiJwtDecoder maiJwtDecode, MaiJwtDecodeToUserModel maiJwtDecodeToUserModel) {
        this.maiJwtDecode = maiJwtDecode;
        this.maiJwtDecodeToUserModel = maiJwtDecodeToUserModel;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        checkTokenExist(request)
                .map(maiJwtDecode::decodedJWT)
                .map(maiJwtDecodeToUserModel::convertNow)
                .map(UserAuthenticateToken::new)
                .ifPresent(userAuthenticateToken -> SecurityContextHolder.getContext().setAuthentication(userAuthenticateToken));
        //Filter request and response of API if not authenticate
        filterChain.doFilter(request,response);
    }

    /**
     * Check existing token in headers authorization and return token or empty optional
     * @param request HttpServletRequest give access to headers etc
     * @return Token or null
     */
    private Optional<String> checkTokenExist(HttpServletRequest request) {
        final String headerAuth = request.getHeader("Authorization");
        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(headerAuth.substring(7));
    }
}
