package br.com.illusion.domain.dto;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class AddressDTO implements Serializable {

  private static final long serialVersionUID = -2288743590177384513L;

  private Long id;

  private String street;

  private String addressNumber;

  private String district;

  private String zipCode;

  private String state;
}
