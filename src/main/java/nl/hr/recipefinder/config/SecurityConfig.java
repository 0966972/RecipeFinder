package nl.hr.recipefinder.config;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final SessionService sessionService;

  @Bean
  protected AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(encoder);
    provider.setUserDetailsService(sessionService);

    return provider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    assert auth != null;
    auth.authenticationProvider(authenticationProvider(passwordEncoder()));
  }

  @Bean
  protected PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .headers().frameOptions().disable()
      .and()
      .cors().configurationSource(corsConfigurationSource())
      .and()
      .httpBasic()
      .and()
      .authorizeRequests()
      .antMatchers(
        "/admin/**",
        "/swagger-ui/**",
        "/h2-console/**"
      ).hasRole(Role.ADMIN.name())
      .antMatchers(
        "/index.html",
        "/",
        "/home",
        "/login",
        "/session/**",
        "/user/**",
        "/recipe/**",
        "/picture/**"
      ).permitAll()
      .anyRequest().authenticated()
      .and()
      .csrf()
      .ignoringAntMatchers(
        "/user/**",
        "/ingredient/**",
        "/recipeIngredient/**",
        "/recipe/**",
        "/h2-console/**",
        "/picture/**"
      ).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(ImmutableList.of("*"));
    configuration.setAllowedMethods(ImmutableList.of("HEAD",
      "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(ImmutableList.of("*"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
