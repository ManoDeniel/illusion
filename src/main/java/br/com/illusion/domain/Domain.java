package br.com.illusion.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;

@lombok.Getter
@lombok.Setter
public class Domain implements Serializable {

  private static final long serialVersionUID = -9202833021213962964L;

  @Column(name = "REGISTRATION_DATE")
  private LocalDate registrationDate;
}
