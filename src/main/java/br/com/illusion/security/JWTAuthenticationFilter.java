package br.com.illusion.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.illusion.domain.Client;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public static final int TOKEN_EXPIRATION = 864_000_000;

  // TODO levar para yml
  public static String TOKEN_PASSWORD = "42116312-34c8-422a-8955-f732240092ef";

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(final AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response) throws AuthenticationException {
    try {
      final Client client = new ObjectMapper().readValue(request.getInputStream(), Client.class);
      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        client.getEmail(), client.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException("Authentication failed by user", e);
    }
  }

  @Override
  protected void successfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain,
      final Authentication authResult) throws IOException, ServletException {
    final Client client = (Client) authResult.getPrincipal();
    final String token = JWT.create()
        .withSubject(client.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
        .sign(Algorithm.HMAC512(TOKEN_PASSWORD));
    response.getWriter().write(token);
    response.getWriter().flush();
  }
}
