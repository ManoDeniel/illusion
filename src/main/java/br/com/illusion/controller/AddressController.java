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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.illusion.domain.Address;
import br.com.illusion.domain.Domain;
import br.com.illusion.domain.dto.AddressDTO;
import br.com.illusion.facade.Facade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/address")
@Api(
  value = "address-controller",
  produces = MediaType.APPLICATION_JSON_VALUE,
  tags = "address-controller")
public class AddressController {

  @Autowired private Facade facade;

  @Autowired private ModelMapper modelMapper;

  @GetMapping("/addresses/{addressId}")
  @ApiOperation(
      value = "Return a address by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AddressDTO findById(@PathVariable(name = "addressId") final Long addressId) {
    final Address address = new Address();
    address.setId(addressId);
    final List<? extends Domain> executar = facade.find(address);
    return modelMapper.map(executar.get(0), AddressDTO.class);
  }

  @GetMapping("client/{clientId}/addresses")
  @ApiOperation(
      value = "Return a list of all registered address by client",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<AddressDTO> findAll(@PathVariable(name = "clientId") final Long clientId) {
    final Address addressInput = new Address();
    addressInput.setClientId(clientId);
    return facade.find(addressInput)
        .stream().map(address -> modelMapper.map(address, AddressDTO.class))
        .collect(Collectors.toList());
  }

  @PostMapping("client/{clientId}/addresses")
  @ApiOperation(
      value = "Save a address for a client",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String save(
      @PathVariable(name = "clientId") final Long clientId,
      @RequestBody final AddressDTO addressDTO) {
    final Address addressInput = modelMapper.map(addressDTO, Address.class);
    addressInput.setClientId(clientId);
    return facade.save(addressInput);
  }

  @PutMapping("/addresses")
  @ApiOperation(
      value = "Update a address by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String update(
      @RequestBody final AddressDTO addressDTO) {
    final Address addressInput = modelMapper.map(addressDTO, Address.class);
    return facade.update(addressInput);
  }

  @DeleteMapping("/addresses/{addressId}")
  @ApiOperation(
      value = "Delete address by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String delete(@PathVariable(name = "addressId") final Long addressId) {
    final Address addressInput = Address.builder().id(addressId).build();
    return facade.delete(addressInput);
  }
}
