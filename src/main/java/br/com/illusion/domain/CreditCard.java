package br.com.illusion.domain;

import java.time.LocalDate;

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
@Table(name = "CREDIT_CARD")
public class CreditCard extends Domain {

  private static final long serialVersionUID = 3951804971117832762L;

  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SEQ_CREDIT_CARD")
  @SequenceGenerator(
    name = "SEQ_CREDIT_CARD",
    sequenceName = "SEQ_CREDIT_CARD",
    allocationSize = 1)
  @Id
  @Column(name = "ID", length = 8, nullable = false, updatable = false)
  private Long id;

  @Column(name = "CREDIT_CARD_NUMBER", length = 20)
  private String number;

  @Column(name = "EXPIRATION_DATE")
  private LocalDate expirationDate;

  @Column(name = "NAME", length = 100)
  private String name;

  @Column(name = "CVV", length = 3)
  private String cvv;

  @Column(name = "CLIENT_ID", length = 8, insertable = false, updatable = false)
  private Long clientId;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
  private Client client;
}
