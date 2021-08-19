package br.com.illusion.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.illusion.dao.ClientDAO;
import br.com.illusion.security.JWTAuthenticationFilter;
import br.com.illusion.security.JWTValidateFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final String[] FREE = {
    "/swagger-ui/**"
  };

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
    httpSecurity.csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
        .antMatchers(FREE).permitAll()
        .antMatchers(HttpMethod.POST, "/clients").permitAll()
//        .anyRequest().authenticated()
      .and()
      .addFilter(new JWTAuthenticationFilter(authenticationManager()))
      .addFilter(new JWTValidateFilter(authenticationManager()));
  }
}
