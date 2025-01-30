package com.backend.DuruDuru.global.config;

import com.backend.DuruDuru.global.security.filter.JwtRequestFilter;
import com.backend.DuruDuru.global.security.principal.PrincipalDetailsService;
import com.backend.DuruDuru.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                // Member 관련 접근
                                .requestMatchers("/member/register", "/member/login/kakao", "/member/login/email","/member/refresh").permitAll()
                                // Trade 관련 접근
                                .requestMatchers("/trade", "/trade/ingredient/available", "/trade/near", "/trade/shareUrl", "/trade/recommend/today", "/trade/keyword/alert", "/trade/current", "/trade/history").permitAll()
                                // 다른 엔티티 관련 접근
                                .requestMatchers("/example/**").permitAll()
                                // Ingredient 관련 접근
                                .requestMatchers("/ingredient/receipt/classify", "/ingredient/purchased", "/ingredient/{ingredient_id}/photo").permitAll()
                                .requestMatchers("/ingredient/{ingredient_id}/purchase-date", "/ingredient/{ingredient_id}/expiry-date", "/ingredient/").permitAll()
                                .requestMatchers("/ingredient/{ingredient_id}","/ingredient/{ingredient_id}/category", "/ingredient/{ingredient_id}/storage-type").permitAll()
                                .requestMatchers("/ingredient/search/name", "/ingredient/search/category/list", "/ingredient/category/count").permitAll()
                                .requestMatchers("/ingredient/category/major-to-minor").permitAll()
                                // Fridge 관련 접근
                                .requestMatchers("/fridge/{member_id}/all-ingredients", "/fridge/{member_id}/near-expiry").permitAll()
                                // OCR 관련 접근
                                .requestMatchers("/OCR/receipt", "/OCR/ingredient/{ingredient_id}", "/OCR/{receipt_id/purchase-date").permitAll()
                                // Town 관련 접근
                                .requestMatchers("/town").permitAll()
                                // 기타 관련 접근
                                .requestMatchers("/", "/api-docs/**", "/api-docs/swagger-config/*", "/swagger-ui/*", "/swagger-ui/**", "/v3/api-docs/**", "/image/upload", "/image/delete").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider, principalDetailsService), UsernamePasswordAuthenticationFilter.class)

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
