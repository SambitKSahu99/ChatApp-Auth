package com.elixr.ChatApp_Auth.config;

import com.elixr.ChatApp_Auth.contants.AuthConstants;
import com.elixr.ChatApp_Auth.contants.UrlConstants;
import com.elixr.ChatApp_Auth.filter.JwtAuthenticationEntryPoint;
import com.elixr.ChatApp_Auth.filter.JwtFilter;
import com.elixr.ChatApp_Auth.service.UserDetailServiceImplementation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceImplementation userDetailService;
    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(UserDetailServiceImplementation userDetailService, JwtFilter jwtFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailService = userDetailService;
        this.jwtFilter = jwtFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(corsFilter()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(UrlConstants.LOGIN_API_ENDPOINT).permitAll() // Allow login page without authentication
                        .anyRequest().authenticated()) // All other requests need authentication
                .formLogin(form -> form
                        .loginPage(UrlConstants.LOGIN_API_ENDPOINT) // Set your login page URL
                        .loginProcessingUrl(UrlConstants.LOGIN_PAGE_URL) // Endpoint for login processing
                        .defaultSuccessUrl(UrlConstants.DEFAULT_SUCCESS_URL, true) // Redirect after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(UrlConstants.LOGOUT_URL) // Specify logout URL
                        .logoutSuccessUrl(UrlConstants.LOGOUT_SUCCESS_URL) // Redirect after logout
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))// Return 401 instead of redirect
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(UrlConstants.UI_URL)); // Frontend URL
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        config.setAllowedHeaders(List.of(AuthConstants.ALLOWED_HEADERS)); // Allowed headers
        config.setAllowCredentials(true);
        source.registerCorsConfiguration(AuthConstants.REGISTERED_CORS_PATTERN, config); // Apply to all paths
        return source;
    }
}
