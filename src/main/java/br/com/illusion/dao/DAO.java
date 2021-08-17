package br.com.illusion.dao;

import java.util.List;

import br.com.illusion.domain.Domain;

public interface DAO {

  public String delete(final Domain domain);

  public List<? extends Domain> find(final Domain domain);

  public List<? extends Domain> findAll();

  public Domain findById(final Long id);

  public String save(final Domain domain);

  public String update(final Domain domain);

  public String writeLog(final String log);
}
