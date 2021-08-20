package br.com.illusion.dao;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.illusion.domain.Client;
import br.com.illusion.domain.Domain;
import br.com.illusion.domain.UserDetailsData;
import br.com.illusion.repository.AddressRepository;
import br.com.illusion.repository.ClientRepository;
import br.com.illusion.util.ClientPasswordEncoder;

@Component
public class ClientDAO implements UserDetailsService, DAO {

  @Autowired private ClientPasswordEncoder clientPasswordEncoder;

  @Autowired private ClientRepository clientRepository;

  @Autowired private AddressRepository addressRepository;

  @Override
  public String delete(final Domain domain) {
    final Client client = (Client) domain;
    final Client hasClient = findById(client.getId());
    if (nonNull(hasClient)) {
      hasClient.setEnabled(false);
      clientRepository.save(hasClient);
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

  public Client findClientByUsername(final String username) {
    final Client client = Client.builder().username(username).build();
    return clientRepository.findOne(Example.of(client)).orElse(null);
  }

  public Client findClientByEmail(final String email) {
    final Client client = Client.builder().email(email).build();
    return clientRepository.findOne(Example.of(client)).orElse(null);
  }

  @Override
  public String save(final Domain domain) {
    final Client client = (Client) domain;
    client.setPassword(clientPasswordEncoder.encode(client.getPassword()));
    client.setRegistrationDate(LocalDate.now());
    final Client clientSaved = clientRepository.save(client);
    client.getAddresses().stream().forEach(address -> {
      address.setClient(clientSaved);
    });
    addressRepository.saveAll(client.getAddresses());
    return nonNull(clientSaved)
        ? "Registration completed successfully!"
        : "Internal error of system!";
  }

  @Override
  public String update(final Domain domain) {
    final Client client = (Client) domain;
    final Client hasClient = findById(client.getId());
    if (nonNull(hasClient)) {
      client.setId(hasClient.getId());
      client.setPassword(hasClient.getPassword());
      clientRepository.save(client);
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
