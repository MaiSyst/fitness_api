package com.maisyst.fitness.security.config;

import com.maisyst.fitness.security.jwt.MaiRequestAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author MaiSYST
 * Reject all users unauthorized except those link signIn
 */
@Configuration
@EnableWebSecurity
public class APISecurityConfig {
    private final MaiRequestAuthFilter maiRequestAuthFilter;

    public APISecurityConfig(MaiRequestAuthFilter maiRequestAuthFilter) {
        this.maiRequestAuthFilter = maiRequestAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(maiRequestAuthFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/api/auth/signIn").permitAll()
                    .requestMatchers("/api/auth/signInCustomer").permitAll()
                    .requestMatchers("/api/auth/checkToken").permitAll()
                    .requestMatchers("/api/auth/addUser").permitAll()
				    .anyRequest().authenticated()
			    )
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
