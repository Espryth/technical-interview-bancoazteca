package me.javierca.invoice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "invoices")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "client_id")
  @Positive(message = "Client id must be positive")
  private Long clientId;

  @Setter
  @Positive(message = "Amount must be positive")
  private Double amount;

  @Setter
  private String description;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "creation_date", updatable = false)
  private Date creationDate;

  @PrePersist
  void prePersist() {
    this.creationDate = new Date();
  }

}
