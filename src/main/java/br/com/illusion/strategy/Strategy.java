package br.com.illusion.strategy;

import br.com.illusion.domain.Domain;

public interface Strategy {

  public String process(final Domain domain);

  public Boolean checkInstance(final Domain domain);
}
