package br.com.illusion.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import br.com.illusion.dao.ClientDAO;
import br.com.illusion.security.JWTAuthenticationFilter;
import br.com.illusion.security.JWTValidateFilter;

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final ClientDAO clientDAO;

  private final PasswordEncoder passwordEncoder;

  public SecurityConfiguration(final ClientDAO clienteDAO, final PasswordEncoder passwordEncoder) {
    this.clientDAO = clienteDAO;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder authenticationManager) throws Exception {
    authenticationManager
      .userDetailsService(clientDAO)
      .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().authorizeRequests()
      .antMatchers(HttpMethod.POST, "/clients").permitAll()
      .antMatchers(HttpMethod.GET, "/clients").authenticated()
      .antMatchers(HttpMethod.PUT, "/clients").authenticated()
      .antMatchers(HttpMethod.DELETE, "/clients").authenticated()
      .antMatchers(HttpMethod.GET, "/login").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(new JWTAuthenticationFilter(authenticationManager()))
      .addFilter(new JWTValidateFilter(authenticationManager()))
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return urlBasedCorsConfigurationSource;
  }
}
