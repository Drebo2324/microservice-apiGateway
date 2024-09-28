package com.drebo.microservices.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //endpoints excluded from authorization
    private final String[] freeResourceUrls = {
            "/swagger-ui.html",
            "/swagger-ui/**", "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/aggregate/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                //authorization rules for incoming requests
        return httpSecurity.authorizeHttpRequests(authorize -> authorize
                        //Allow all resource urls
                        .requestMatchers(freeResourceUrls).permitAll()
                        //All request must be authorized
                        .anyRequest().authenticated())
                //server expects requests to have valid JWT for authentication
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
