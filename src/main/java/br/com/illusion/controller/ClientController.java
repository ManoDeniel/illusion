package br.com.illusion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.illusion.domain.Address;
import br.com.illusion.domain.Client;
import br.com.illusion.domain.Domain;
import br.com.illusion.domain.dto.AddressDTO;
import br.com.illusion.domain.dto.ClientDTO;
import br.com.illusion.facade.Facade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/client")
@Api(
  value = "client-controller",
  produces = MediaType.APPLICATION_JSON_VALUE,
  tags = "client-controller")
public class ClientController {

  @Autowired private Facade facade;

  @Autowired private ModelMapper modelMapper;

  @GetMapping("/clients/{clientId}")
  @ApiOperation(
      value = "Return a client by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ClientDTO findById(@PathVariable(name = "clientId") final Long clientId) {
    final Client clienteInput = new Client();
    clienteInput.setId(clientId);
    final List<? extends Domain> executar = facade.find(clienteInput);
    return modelMapper.map(executar.get(0), ClientDTO.class);
  }

  @GetMapping("/clients")
  @ApiOperation(
      value = "Return a list of all registered clients",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ClientDTO> findAll() {
    final Client clientInput = new Client();
    return facade.find(clientInput)
        .stream().map(client -> modelMapper.map(client, ClientDTO.class))
        .collect(Collectors.toList());
  }

  @PostMapping("/clients")
  @ApiOperation(
      value = "Save a client",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String save(@RequestBody final ClientDTO clienteDTO) {
    final Client client = Client.builder()
        .name(clienteDTO.getName())
        .email(clienteDTO.getEmail())
        .username(clienteDTO.getUsername())
        .password(clienteDTO.getPassword())
        .build();

    final List<AddressDTO> addressesDTO = clienteDTO.getAddresses();
    final List<Address> addresses = addressesDTO
        .stream()
        .map(address -> modelMapper.map(address, Address.class))
        .collect(Collectors.toList());

    client.setAddresses(addresses);
    return facade.save(client);
  }

//  @PutMapping("/clients")
//  @ApiOperation(
//      value = "Atualiza as informações de um cliente atráves de um id",
//      produces = MediaType.APPLICATION_JSON_VALUE)
//  public String update(
//      @RequestBody final ClienteUpdateDTO clienteDTO) {
//    final Cliente clienteInput = modelMapper.map(clienteDTO, Cliente.class);
//    return facade.update(clienteInput);
//  }

  @DeleteMapping("/clients/{clientId}")
  @ApiOperation(
      value = "Delete client by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String delete(@PathVariable(name = "clientId") final Long clientId) {
    final Client client = Client.builder().id(clientId).build();
    return facade.delete(client);
  }
}
