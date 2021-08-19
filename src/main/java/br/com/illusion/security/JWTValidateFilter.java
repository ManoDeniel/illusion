package br.com.illusion.security;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTValidateFilter extends BasicAuthenticationFilter {

  public static final String AUTHORIZATION = "Authorization";

  public static final String PREFIX = "Bearer ";

  public JWTValidateFilter(final AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain) throws IOException, ServletException {
    final String headerAttribute = request.getHeader(AUTHORIZATION);
    if (isNull(headerAttribute) || !headerAttribute.startsWith(PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    final String token = headerAttribute.replace(PREFIX, "");
    final UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(final String token) {
    final String cliente = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_PASSWORD))
      .build()
      .verify(token)
      .getSubject();
    if (nonNull(cliente)) {
      return new UsernamePasswordAuthenticationToken(cliente, null, new ArrayList<>());
    } else {
      return null;
    }
  }
}
