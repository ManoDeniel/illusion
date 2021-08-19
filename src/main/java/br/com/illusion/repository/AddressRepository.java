package br.com.illusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.illusion.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> { }
