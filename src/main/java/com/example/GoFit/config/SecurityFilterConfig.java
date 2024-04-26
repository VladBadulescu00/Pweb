package com.example.GoFit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Locale;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests.requestMatchers(
                                                "/users/**",
                                                "/swagger-ui/**",
                                                "/v2/**",
                                                "/webjars/**",
                                                "/v3/**",
                                                "/**"
                                        )
                                        .permitAll()
                                        .anyRequest().authenticated()
                );


        return http.build();
    }

}