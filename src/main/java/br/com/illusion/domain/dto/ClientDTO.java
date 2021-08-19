package br.com.illusion.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class ClientDTO implements Serializable {

  private static final long serialVersionUID = -2637061058920671644L;

  private Long id;

  private String name;

  private String email;

  private String username;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean enabled;

  private List<AddressDTO> addresses;
}
