package bushigen.nongo.config;

import bushigen.nongo.security.AuthorizeFilter;
import bushigen.nongo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtUtil jwtUtil;

  /**
   * Use the new AuthenticationManager bean instead of manually creating DaoAuthenticationProvider
   */
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }

  /**
   * CORS設定
   * CSRF無効化
   * 認証不要パス設定
   * デフォルトのログアウトハンドラを無効化
   * 認証フィルター設定
   */
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(c -> c.configurationSource(corsConfigurationSource()))
      .csrf(csrf -> csrf.disable())
      .logout(logout -> logout.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          "/",
          "/index.html",
          "/favicon.ico",
          "/_app/**",
          "/login",
          "/signup",
          "/logout",
          "/is-token-valid",
          "/verify-email",
          "/api-docs*/**",
          "/swagger-ui/**"
        ).permitAll()
        .anyRequest().authenticated()
      )
      .addFilterBefore(
        new AuthorizeFilter(jwtUtil),
        UsernamePasswordAuthenticationFilter.class
      );

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * CORS設定
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cors = new CorsConfiguration();
    cors.setAllowedOrigins(List.of("http://localhost:5173"));
    cors.setAllowedMethods(List.of("GET", "POST"));
    cors.setAllowedHeaders(List.of("*"));
    cors.setExposedHeaders(List.of("Authorization"));
    cors.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    src.registerCorsConfiguration("/**", cors);
    return src;
  }
}
