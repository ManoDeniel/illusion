package br.com.illusion.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.illusion.domain.Client;

@Component
public class StrategiesLoader {

  @Autowired private ValidateEmail validateEmail;

  public Map<String, List<Strategy>> load() {
    final Map<String, List<Strategy>> strategies = new HashMap<>();

    strategies.put(Client.class.getName(), loadClient());
    return strategies;
  }

  private List<Strategy> loadClient() {
    final List<Strategy> strategies = new ArrayList<>();
    strategies.add(validateEmail);
    return strategies;
  }
}
