package me.javierca.client.repository;

import me.javierca.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  @Query("SELECT client FROM Client client WHERE client.name = ?1 AND client.paternalSurname = ?2 AND client.maternalSurname = ?3")
  Optional<Client> findByFullName(final String name, final String paternalSurname, final String maternalSurname);

}
