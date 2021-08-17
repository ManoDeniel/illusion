package br.com.illusion.facade;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.illusion.dao.DAO;
import br.com.illusion.dao.DaosLoader;
import br.com.illusion.domain.Domain;
import br.com.illusion.strategy.StrategiesLoader;
import br.com.illusion.strategy.Strategy;

@Service
public class Facade {

  private Map<String, DAO> allDao;

  private Map<String, List<Strategy>> allStrategy;

  @Autowired
  public Facade(final StrategiesLoader strategiesLoader, final DaosLoader daosLoader) {
    allDao = daosLoader.load();
    allStrategy = strategiesLoader.load();
  }

  public String delete(final Domain domain) {
    final DAO dao = allDao.get(domain.getClass().getName());
    return dao.delete(domain);
  }

  public List<? extends Domain> find(final Domain domain) {
    final DAO dao = allDao.get(domain.getClass().getName());
    return dao.find(domain);
  }

  public String save(final Domain domain) {
    String logErro = null;
    final List<Strategy> strategies = allStrategy.get(domain.getClass().getName());
    if (nonNull(strategies)) {
      for (final Strategy strategy : strategies) {
        logErro = strategy.process(domain);
        if (!isNullOrEmpty(logErro)) {
          return logErro;
        }
      }
    }
    final DAO dao = allDao.get(domain.getClass().getName());
    return dao.save(domain);
  }

  public String update(final Domain domain) {
    String logErro = null;
    final List<Strategy> strategies = allStrategy.get(domain.getClass().getName());
    if (nonNull(strategies)) {
      for (final Strategy strategy : strategies) {
        logErro = strategy.process(domain);
        if (!isNullOrEmpty(logErro)) {
          return logErro;
        }
      }
    }
    final DAO dao = allDao.get(domain.getClass().getName());
    return dao.update(domain);
  }
}
