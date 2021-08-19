package br.com.illusion.domain.dto;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class ClientUpdateDTO implements Serializable {

  private static final long serialVersionUID = -6594832953952468373L;

  private Long id;

  private String name;

  private String email;

  private String username;
}
