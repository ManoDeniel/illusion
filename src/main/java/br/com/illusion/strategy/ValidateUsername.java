package br.com.illusion.strategy;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.illusion.dao.ClientDAO;
import br.com.illusion.domain.Client;
import br.com.illusion.domain.Domain;

@Component
public class ValidateUsername implements Strategy {

  @Autowired private ClientDAO clientDAO;

  @Override
  public String process(final Domain domain) {
    if (checkInstance(domain)) {
      final Client client = (Client) domain;
      return checkUsername(client);
    }
    return "Internal error of system!";
  }

  @Override
  public Boolean checkInstance(final Domain domain) {
    return domain instanceof Client;
  }

  public String checkUsername(final Client client) {
    if (!isNullOrEmpty(client.getUsername())) {
      final Client clientUsername = clientDAO.findClientByUsername(client.getUsername());
      if (isNull(clientUsername)) {
        return null;
      } else if (clientUsername.getId().equals(client.getId())) {
        return null;
      } else {
        return "Username unavailable!";
      }
    } else {
      if (nonNull(client.getId())) {
        return null;
      }
      return "Username cannot be empty!";
    }
  }
}
