package nl.hr.recipefinder.config;

import lombok.RequiredArgsConstructor;
import nl.hr.recipefinder.security.Role;
//import nl.hr.recipefinder.service.UserDetailService;
import nl.hr.recipefinder.service.UserDetailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailService userDetailService;

  @Bean
  protected AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setPasswordEncoder(encoder);
      provider.setUserDetailsService(userDetailService);

      return provider;
  }

  @Bean
  protected PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
      assert auth != null;
      auth.authenticationProvider(authenticationProvider(passwordEncoder()));
  }

//  @Override
//  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//      auth.inMemoryAuthentication()
//            .withUser("user1").password(passwordEncoder().encode("minor")).roles("USER")
//            .and()
//            .withUser("user2").password(passwordEncoder().encode("minor")).roles("USER")
//            .and()
//            .withUser("admin1").password(passwordEncoder().encode("minor")).roles("ADMIN")
//            .and()
//            .withUser("admin2").password(passwordEncoder().encode("minor")).roles("ADMIN");
//  }

  @Override
  protected void configure(HttpSecurity http)
    throws Exception {
    http
      .csrf()
      .disable()
      .cors()
      .and()
      .authorizeRequests()
      .antMatchers("/index.html", "/", "/home", "/login", "/users", "/admin").permitAll()
      .antMatchers("/swagger-ui", "/h2-console").permitAll()
      .antMatchers("/user").permitAll()
      //.antMatchers("/admin/**").hasRole(Role.ADMIN.name())
      .anyRequest().authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}
