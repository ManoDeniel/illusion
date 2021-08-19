package br.com.illusion.dao;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import br.com.illusion.domain.Address;
import br.com.illusion.domain.Client;
import br.com.illusion.domain.Domain;
import br.com.illusion.repository.AddressRepository;
import br.com.illusion.repository.ClientRepository;

@Component
public class AddressDAO implements DAO {

  @Autowired private ClientRepository clientRepository;

  @Autowired private AddressRepository addressRepository;

  @Override
  public String delete(final Domain domain) {
    final Address address = (Address) domain;
    final Address hasAddress = findById(address.getId());
    if (nonNull(hasAddress)) {
      addressRepository.deleteById(address.getId());
      return "Address successfully deleted!";
    } else {
      return "Internal error of system! Address not found!";
    }
  }

  @Override
  public List<? extends Domain> find(final Domain domain) {
    final Address address = (Address) domain;
    List<Address> addresses = new ArrayList<>();
    if (nonNull(address.getId())) {
      addresses.add(findById(address.getId()));
    } else if (nonNull(address.getClientId())) {
      addresses = findAllByClientId(address.getClientId());
    } else {
      addresses = findAll();
    }
    return addresses;
  }

  @Override
  public List<Address> findAll() {
    return addressRepository.findAll();
  }

  public List<Address> findAllByClientId(final Long clientId) {
    return addressRepository.findAll(Example.of(Address.builder().clientId(clientId).build()));
  }

  @Override
  public Address findById(final Long id) {
    return addressRepository.findById(id).orElse(null);
  }

  @Override
  public String save(final Domain domain) {
    final Address address = (Address) domain;
    address.setRegistrationDate(LocalDate.now());
    final Client client = clientRepository.findById(address.getClientId()).orElse(null);
    if (nonNull(client)) {
      address.setClient(client);
      addressRepository.save(address);
      return "Registration completed successfully!";
    } else {
      return "Internal error of system!";
    }
  }

  @Override
  public String update(final Domain domain) {
    final Address address = (Address) domain;
    final Address hasAddress = findById(address.getId());
    if (nonNull(hasAddress)) {
      address.setId(hasAddress.getId());
      address.setClient(hasAddress.getClient());
      addressRepository.save(address);
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
