package br.com.illusion.util;

import static com.google.common.base.Strings.isNullOrEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ClientPasswordEncoder {

  @Autowired private PasswordEncoder passwordEncoder;

  public String encode(final String password) {
    if (isNullOrEmpty(password)) {
      return "Internal error of system!";
    }
    return passwordEncoder.encode(password);
  }
}
