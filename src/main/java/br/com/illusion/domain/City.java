package br.com.illusion.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
@Entity
@Table(name = "CITY")
public class City implements Serializable {

  private static final long serialVersionUID = 7572099271139472607L;

  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SEQ_CITY")
  @SequenceGenerator(
    name = "SEQ_CITY",
    sequenceName = "SEQ_CITY",
    allocationSize = 1)
  @Id
  @Column(name = "ID", length = 8, nullable = false, updatable = false)
  private Long id;

  @Column(name = "NAME", length = 100)
  private String name;

  @Column(name = "STATE_ID", length = 8, insertable = false, updatable = false)
  private String stateId;

  @ManyToOne
  @JoinColumn(name = "STATE_ID", referencedColumnName = "ID")
  private State state;
}
