package br.com.illusion.strategy;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.nonNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.illusion.dao.ClientDAO;
import br.com.illusion.domain.Client;
import br.com.illusion.domain.Domain;

@Component
public class ValidateEmail implements Strategy {

  @Autowired private ClientDAO clientDAO;

  @Override
  public String process(final Domain domain) {
    if (checkInstance(domain)) {
      final Client client = (Client) domain;
      return verificarEmail(client);
    }
    return "Erro interno do sistema!";
  }

  @Override
  public Boolean checkInstance(final Domain domain) {
    return domain instanceof Client;
  }

  public String verificarEmail(final Client client) {
    if (!isNullOrEmpty(client.getEmail())) {
      final Optional<Client> clientEmail = clientDAO.findClientByEmail(client.getEmail());
      if (clientEmail.isEmpty()) {
        return null;
      } else if (clientEmail.get().getId().equals(client.getId())) {
        return null;
      } else {
        return "Email unavailable!";
      }
    } else {
      if (nonNull(client.getId())) {
        return null;
      }
      return "Email cannot be empty!";
    }
  }
}
