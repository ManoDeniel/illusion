package br.com.illusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.illusion.domain.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> { }
