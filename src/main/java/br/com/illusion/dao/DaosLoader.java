package br.com.illusion.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.illusion.domain.Address;
import br.com.illusion.domain.Client;

@Component
public class DaosLoader {

  @Autowired private AddressDAO addressDAO;

  @Autowired private ClientDAO clienteDAO;

  public Map<String, DAO> load() {
    final Map<String, DAO> daos = new HashMap<>();
    daos.put(Address.class.getName(), addressDAO);
    daos.put(Client.class.getName(), clienteDAO);
    return daos;
  }
}
