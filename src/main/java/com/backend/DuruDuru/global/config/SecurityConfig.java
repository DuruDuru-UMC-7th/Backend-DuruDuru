package com.backend.DuruDuru.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                // Member 관련 접근
                                .requestMatchers("/member/sign-up").permitAll()
                                // 다른 엔티티 관련 접근
                                .requestMatchers("/example/**").permitAll()
                                // Ingredient 관련 접근
                                .requestMatchers("/ingredient/receipt/upload", "/ingredient/receipt/classify", "/ingredient/purchased", "/ingredient/photo", "/ingredient/category").permitAll()
                                .requestMatchers("/ingredient/purchase-date", "/ingredient/expiry-date", "/ingredient/", "/ingredient/", "/ingredient/search/name/list", "/ingredient/search/category/list", "/ingredient/category/add").permitAll()
                                // 기타 관련 접근
                                .requestMatchers("/", "/api-docs/**", "/api-docs/swagger-config/*", "/swagger-ui/*", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                )
                //.addFilterBefore(new JwtAuthenticationFilter(), SecurityFilterChain.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*", "http://localhost:3000"));
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
