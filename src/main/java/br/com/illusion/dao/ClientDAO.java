package br.com.illusion.dao;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.illusion.domain.Client;
import br.com.illusion.domain.Domain;
import br.com.illusion.domain.UserDetailsData;
import br.com.illusion.repository.ClientRepository;
import br.com.illusion.util.ClientPasswordEncoder;

@Component
public class ClientDAO implements UserDetailsService, DAO {

  @Autowired private ClientPasswordEncoder clientPasswordEncoder;

  @Autowired private ClientRepository clientRepository;

  @Override
  public String delete(final Domain domain) {
    final Client client = (Client) domain;
    final Boolean hasClient = clientRepository.existsById(client.getId());
    if (hasClient) {
      clientRepository.deleteById(client.getId());
      return "Client successfully deleted!";
    } else {
      return "Internal error of system! Client not found!";
    }
  }

  @Override
  public List<? extends Domain> find(final Domain domain) {
    final Client client = (Client) domain;
    List<Client> clients = new ArrayList<>();
    if (nonNull(client.getId())) {
      clients.add(findById(client.getId()));
    } else {
      clients = findAll();
    }
    return clients;
  }

  @Override
  public List<Client> findAll() {
    return clientRepository.findAll();
  }

  @Override
  public Client findById(final Long id) {
    return clientRepository.findById(id).orElse(null);
  }

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final Example<Client> clientExample = Example.of(Client.builder().username(username).build());
    return new UserDetailsData(clientRepository.findOne(clientExample)
        .orElseThrow(() -> new UsernameNotFoundException(username)));
  }

  public Optional<Client> findClientByEmail(final String email) {
    final Client client = Client.builder().email(email).build();
    return clientRepository.findOne(Example.of(client));
  }

  @Override
  public String save(final Domain domain) {
    final Client client = (Client) domain;
    client.setPassword(clientPasswordEncoder.encode(client.getPassword()));
    client.setRegistrationDate(LocalDate.now());
    clientRepository.save(client);
    return "Registration completed successfully!";
  }

  @Override
  public String update(Domain domain) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String writeLog(String log) {
    // TODO Auto-generated method stub
    return null;
  }
}
