package me.javierca.client.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "clients")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Paternal surname is mandatory")
  @Column(name = "paternal_surname")
  private String paternalSurname;

  @NotBlank(message = "Maternal surname is mandatory")
  @Column(name = "maternal_surname")
  private String maternalSurname;

  @Email(message = "Email should be valid")
  private String email;

  @Pattern(regexp = "\\d{10}", message = "Phone should have 10 digits")
  private String phone;

  @Temporal(TemporalType.DATE)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private Date birthdate;

  @Enumerated(EnumType.STRING)
  private Gender gender = Gender.OTHER;

}
