package com.CodeCrafters.complaint_management;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// *** THIS IMPORT IS VERY IMPORTANT AND EASY TO MISS ***
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Apply CORS configuration using the bean below
            .cors(withDefaults())
            // 2. Disable CSRF protection (This is the fix for the 403 error)
            .csrf(csrf -> csrf.disable())
            // 3. Define authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to access the /api/auth/** endpoints
                .requestMatchers("/api/auth/**").permitAll()
                // Require authentication for any other request
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Set the allowed origins
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173", "http://localhost", "capacitor://localhost"));
        // Set the allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Set the allowed headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Allow credentials
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all endpoints in the application
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
