package br.com.illusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.illusion.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  @Query("SELECT client FROM Client client WHERE client.username = :username")
  Client findByUsername(@Param("username") final String username);
}
