package br.com.illusion.dao;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import br.com.illusion.domain.Client;
import br.com.illusion.domain.CreditCard;
import br.com.illusion.domain.Domain;
import br.com.illusion.repository.ClientRepository;
import br.com.illusion.repository.CreditCardRepository;

@Component
public class CreditCardDAO implements DAO {

  @Autowired private ClientRepository clientRepository;

  @Autowired private CreditCardRepository creditCardRepository;

  @Override
  public String delete(final Domain domain) {
    final CreditCard creditCard = (CreditCard) domain;
    final CreditCard hasCreditCard = findById(creditCard.getId());
    if (nonNull(hasCreditCard)) {
      creditCardRepository.deleteById(creditCard.getId());
      return "Address successfully deleted!";
    } else {
      return "Internal error of system! Address not found!";
    }
  }

  @Override
  public List<? extends Domain> find(final Domain domain) {
    final CreditCard creditCard = (CreditCard) domain;
    List<CreditCard> creditCards = new ArrayList<>();
    if (nonNull(creditCard.getId())) {
      creditCards.add(findById(creditCard.getId()));
    } else if (nonNull(creditCard.getClientId())) {
      creditCards = findAllByClientId(creditCard.getClientId());
    } else {
      creditCards = findAll();
    }
    return creditCards;
  }

  @Override
  public List<CreditCard> findAll() {
    return creditCardRepository.findAll();
  }

  public List<CreditCard> findAllByClientId(final Long clientId) {
    return creditCardRepository.findAll(Example.of(CreditCard.builder().clientId(clientId).build()));
  }

  @Override
  public CreditCard findById(final Long id) {
    return creditCardRepository.findById(id).orElse(null);
  }

  @Override
  public String save(final Domain domain) {
    final CreditCard creditCard = (CreditCard) domain;
    creditCard.setRegistrationDate(LocalDate.now());
    final Client client = clientRepository.findById(creditCard.getClientId()).orElse(null);
    if (nonNull(client)) {
      creditCard.setClient(client);
      creditCardRepository.save(creditCard);
      return "Registration completed successfully!";
    } else {
      return "Internal error of system!";
    }
  }

  @Override
  public String update(final Domain domain) {
    final CreditCard creditCard = (CreditCard) domain;
    final CreditCard hasCreditCard = findById(creditCard.getId());
    if (nonNull(hasCreditCard)) {
      creditCard.setId(hasCreditCard.getId());
      creditCard.setClient(hasCreditCard.getClient());
      creditCardRepository.save(creditCard);
      return "Update completed successfully!";
    } else {
      return "Internal error of system!";
    }
  }

  @Override
  public String writeLog(String log) {
    // TODO Auto-generated method stub
    return null;
  }

}
