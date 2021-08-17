package br.com.illusion.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
@Entity
@Table(name = "CLIENT")
public class Client extends Domain implements Serializable {

  private static final long serialVersionUID = 4779766669141268556L;

  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SEQ_CLIENTE")
  @SequenceGenerator(
    name = "SEQ_CLIENTE",
    sequenceName = "SEQ_CLIENTE",
    allocationSize = 1)
  @Id
  @Column(name = "ID", length = 8, nullable = false, updatable = false)
  private Long id;

  @Column(name = "NAME", length = 100)
  private String name;

  @Column(name = "EMAIL", length = 100)
  private String email;

  @Column(name = "USERNAME", length = 40)
  private String username;

  @Column(name = "PASSWORD", length = 70)
  private String password;

  @OneToMany(mappedBy = "client")
  private List<Address> Addresses;
}
