package com.pushkar.developerlifeos.config;

import com.pushkar.developerlifeos.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // ==========================
                // CSRF
                // ==========================

                .csrf(csrf -> csrf.disable())


                // ==========================
                // CORS
                // ==========================

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )


                // ==========================
                // Stateless Session
                // ==========================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // ==========================
                // Authorization
                // ==========================

                .authorizeHttpRequests(auth -> auth

                        // --------------------------------
                        // Public Authentication APIs
                        // --------------------------------

                        .requestMatchers(
                                "/auth/**"
                        ).permitAll()


                        // --------------------------------
                        // Swagger / OpenAPI
                        // --------------------------------

                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()


                        // --------------------------------
                        // CORS Preflight
                        // --------------------------------

                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()


                        // --------------------------------
                        // Admin-only Task Delete
                        // --------------------------------

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/tasks/**"
                        ).hasRole("ADMIN")


                        // --------------------------------
                        // All Application APIs
                        // Require Login
                        // --------------------------------

                        .requestMatchers(
                                "/api/**"
                        ).authenticated()


                        // --------------------------------
                        // Everything else
                        // --------------------------------

                        .anyRequest().authenticated()
                )


                // ==========================
                // JWT Filter
                // ==========================

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    // ==========================
    // Password Encoder
    // ==========================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // ==========================
    // CORS Configuration
    // ==========================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}