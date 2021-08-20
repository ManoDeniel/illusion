package br.com.illusion.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class CreditCardDTO implements Serializable {

  private static final long serialVersionUID = -2288743590177384513L;

  private Long id;

  private String number;

  private LocalDate expirationDate;

  private String name;

  private String cvv;
}
