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
@Table(name = "STATE")
public class State implements Serializable {

  private static final long serialVersionUID = 7572099271139472607L;

  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SEQ_STATE")
  @SequenceGenerator(
    name = "SEQ_STATE",
    sequenceName = "SEQ_STATE",
    allocationSize = 1)
  @Id
  @Column(name = "ID", length = 8, nullable = false, updatable = false)
  private Long id;

  @Column(name = "NAME", length = 100)
  private String name;

  @Column(name = "ACRONYM", length = 2)
  private String acronym;

  @OneToMany(mappedBy = "state")
  private List<City> cities;
}
