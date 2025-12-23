package com.rafaelalvarado.portfolio.config;

import com.rafaelalvarado.portfolio.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración de Spring Security.
 * 
 * Esta clase configura la seguridad de la aplicación, incluyendo:
 * - CORS para permitir peticiones desde el frontend Angular
 * - Endpoints públicos y protegidos
 * - Autenticación JWT
 * - Encriptación de contraseñas con BCrypt
 * 
 * @author Rafael Alvarado García
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    /**
     * Configura el encoder de contraseñas (BCrypt).
     * 
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el AuthenticationManager.
     * 
     * @param authConfig Configuración de autenticación
     * @return AuthenticationManager
     * @throws Exception Si ocurre un error
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configura CORS para permitir peticiones desde el frontend.
     * 
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * 
     * Define qué endpoints son públicos y cuáles requieren autenticación.
     * 
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Si ocurre un error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (usamos JWT)
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Configurar autorización de endpoints
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/blog/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/contact").permitAll()

                        // Endpoints protegidos (requieren autenticación)
                        .requestMatchers(HttpMethod.POST, "/blog/posts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/blog/posts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/blog/posts/**").authenticated()
                        .requestMatchers("/contact/messages/**").authenticated()
                        .requestMatchers("/users/**").authenticated() // Gestión de usuarios

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated())

                // Configurar sesión stateless (sin sesiones, solo JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Agregar filtro JWT antes del filtro de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
