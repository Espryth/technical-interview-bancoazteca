package me.javierca.client.service;

import lombok.extern.slf4j.Slf4j;
import me.javierca.client.entity.Client;
import me.javierca.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ClientService {

  private final ClientRepository clientRepository;

  @Autowired
  ClientService(final ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public ResponseEntity<Client> getClient(final Long id) {
    log.info("Getting client with id: {}", id);
    return this.clientRepository.findById(id)
        .map(client -> {
          log.info("Client found: {}", client);
          return ResponseEntity.ok(client);
        })
        .orElseGet(() -> {
          log.warn("Client not found");
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
  }

  public ResponseEntity<List<Client>> getClients() {
    return ResponseEntity.ok(this.clientRepository.findAll());
  }

  public ResponseEntity<String> createClient(final Client client) {
    log.info("Creating client: {}", client);
    return this.clientRepository.findByFullName(client.getName(), client.getPaternalSurname(), client.getMaternalSurname())
        .map(model -> {
          log.error("Client with name {} and surnames {} {} already exists", model.getName(), model.getPaternalSurname(), model.getMaternalSurname());
          return ResponseEntity.status(HttpStatus.CONFLICT)
              .body("Client already exists");
        })
        .orElseGet(() -> {
          this.clientRepository.save(client);
          log.info("Client not found, creating new client with id: {}", client.getId());
          return ResponseEntity.ok(String.valueOf(client.getId()));
        });
  }

  @Transactional
  public ResponseEntity<Client> deleteClient(final Long id) {
    log.info("Deleting client with id: {}", id);
    return this.clientRepository.findById(id)
        .map(client -> {
          log.info("Client deleted: {}", client);
          this.clientRepository.delete(client);
          return ResponseEntity.ok(client);
        })
        .orElseGet(() -> {
          log.error("Cannot delete client with id: {}, because it does not exist", id);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
  }
}
