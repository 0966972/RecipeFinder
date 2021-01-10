package nl.hr.recipefinder.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.security.Role;
import nl.hr.recipefinder.service.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
      // admin
      .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
      // ingredient
      .antMatchers(HttpMethod.GET, "/ingredient/**").permitAll()
      .antMatchers(HttpMethod.POST, "/ingredient").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
      // picture
      .antMatchers(HttpMethod.GET, "/picture/**").permitAll()
      // recipe
      .antMatchers(HttpMethod.GET, "/recipe/**").permitAll()
      .antMatchers(HttpMethod.POST, "/recipe/search/**").permitAll()
      .antMatchers(HttpMethod.POST, "/recipe/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
      // recipeIngredient
      .antMatchers(HttpMethod.POST, "/recipeIngredient").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
      // report
      .antMatchers(HttpMethod.GET, "/report/**").hasRole(Role.ADMIN.name())
      .antMatchers(HttpMethod.POST, "/report").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
      // review
      .antMatchers(HttpMethod.GET, "/review/**").permitAll()
      // session
      .antMatchers(HttpMethod.GET, "/session/**").permitAll()
      // user
      .antMatchers("/user/**").permitAll()
      // other
      .antMatchers("/swagger-ui/**", "/h2-console/**").hasRole(Role.ADMIN.name())
      .anyRequest().authenticated()
      .and()
      .csrf().disable();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Lists.newArrayList("http://localhost:4200"));
    configuration.setAllowedMethods(ImmutableList.of("HEAD",
      "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(ImmutableList.of("*"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
