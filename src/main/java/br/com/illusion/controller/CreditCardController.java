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

import br.com.illusion.domain.CreditCard;
import br.com.illusion.domain.Domain;
import br.com.illusion.domain.dto.CreditCardDTO;
import br.com.illusion.facade.Facade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/credit-card")
@Api(
  value = "credit-card-controller",
  produces = MediaType.APPLICATION_JSON_VALUE,
  tags = "credit-card-controller")
public class CreditCardController {

  @Autowired private Facade facade;

  @Autowired private ModelMapper modelMapper;

  @GetMapping("/credit-cards/{creditCardId}")
  @ApiOperation(
      value = "Return a credit card by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CreditCardDTO findById(@PathVariable(name = "creditCardId") final Long creditCardId) {
    final CreditCard creditCard = new CreditCard();
    creditCard.setId(creditCardId);
    final List<? extends Domain> executar = facade.find(creditCard);
    return modelMapper.map(executar.get(0), CreditCardDTO.class);
  }

  @GetMapping("client/{clientId}/credit-cards")
  @ApiOperation(
      value = "Return a list of all registered credit cards by client",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CreditCardDTO> findAll(@PathVariable(name = "clientId") final Long clientId) {
    final CreditCard creditCardInput = new CreditCard();
    creditCardInput.setClientId(clientId);
    return facade.find(creditCardInput)
        .stream().map(creditCard -> modelMapper.map(creditCard, CreditCardDTO.class))
        .collect(Collectors.toList());
  }

  @PostMapping("client/{clientId}/credit-cards")
  @ApiOperation(
      value = "Save a credit card for a client",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String save(
      @PathVariable(name = "clientId") final Long clientId,
      @RequestBody final CreditCardDTO creditCardDTO) {
    final CreditCard creditCardInput = modelMapper.map(creditCardDTO, CreditCard.class);
    creditCardInput.setClientId(clientId);
    return facade.save(creditCardInput);
  }

  @PutMapping("/credit-cards")
  @ApiOperation(
      value = "Update a credit card by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String update(
      @RequestBody final CreditCardDTO creditCardDTO) {
    final CreditCard creditCardInput = modelMapper.map(creditCardDTO, CreditCard.class);
    return facade.update(creditCardInput);
  }

  @DeleteMapping("/credit-cards/{creditCardId}")
  @ApiOperation(
      value = "Delete credit card by id",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String delete(@PathVariable(name = "creditCardId") final Long creditCardId) {
    final CreditCard creditCardInput = CreditCard.builder().id(creditCardId).build();
    return facade.delete(creditCardInput);
  }
}
