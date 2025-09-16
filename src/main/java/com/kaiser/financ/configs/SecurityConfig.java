package com.kaiser.financ.configs;

import com.kaiser.financ.security.JWTAuthenticationFilter;
import com.kaiser.financ.security.JWTAuthorizationFilter;
import com.kaiser.financ.security.JWTUtil;
import com.kaiser.financ.services.UsuarioService;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
  private static final String[] PUBLIC_MATCHERS_GET = {};
  private static final String[] PUBLIC_MATCHERS_POST = {
      "/usuarios/", "/auth/forgot/**", "/auth/reset_password/**"
  };

  private final JWTUtil jwtUtil;
  private final Environment env;

  public SecurityConfig(JWTUtil jwtUtil, Environment env) {
    this.jwtUtil = jwtUtil;
    this.env = env;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authManager,
      CorsConfigurationSource corsConfigurationSource,
      UsuarioService usuarioService,
      org.springframework.security.core.userdetails.UserDetailsService userDetailsService) throws Exception {

    // H2 console em perfil de teste
    if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
      http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
    }

    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
            .requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
            .requestMatchers(PUBLIC_MATCHERS).permitAll()
            .anyRequest().hasRole("USER")
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // JWT filters recebem os services via parâmetro
    http.addFilter(new JWTAuthenticationFilter(authManager, jwtUtil, usuarioService));
    http.addFilterBefore(
        new JWTAuthorizationFilter(authManager, jwtUtil, userDetailsService),
        UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Arrays.asList("*")); // ajustar para produção
    configuration.setAllowedHeaders(Arrays.asList("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
