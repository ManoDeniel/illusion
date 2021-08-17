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
@Table(name = "ADDRESS")
public class Address implements Serializable {

  private static final long serialVersionUID = 7572099271139472607L;

  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SEQ_ADDRESS")
  @SequenceGenerator(
    name = "SEQ_ADDRESS",
    sequenceName = "SEQ_ADDRESS",
    allocationSize = 1)
  @Id
  @Column(name = "ID", length = 8, nullable = false, updatable = false)
  private Long id;

  @Column(name = "STREET", length = 100)
  private String street;

  @Column(name = "ADDRESS_NUMBER", length = 10)
  private String addressNumber;

  @Column(name = "DISTRICT", length = 80)
  private String district;

  @Column(name = "ZIP_CODE", length = 12)
  private String zipCode;

  @Column(name = "STATE", length = 80)
  private String state;

//  @Column(name = "CITY_ID", length = 8, insertable = false, updatable = false)
//  private String cityId;

  @Column(name = "CLIENT_ID", length = 8, insertable = false, updatable = false)
  private String clientId;

//  @OneToOne
//  @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
//  private City city;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
  private Client client;
}
