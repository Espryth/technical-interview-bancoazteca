package me.javierca.client.controller;

import me.javierca.client.entity.Client;
import me.javierca.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

  private final ClientService clientService;

  @Autowired
  ClientController(final ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/create")
  ResponseEntity<?> createClient(final @Valid @RequestBody Client client, final BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    return this.clientService.createClient(client);
  }

  @DeleteMapping("/delete/{id}")
  ResponseEntity<Client> deleteClient(final @PathVariable Long id) {
    return this.clientService.deleteClient(id);
  }

  @GetMapping("/get/{id}")
  ResponseEntity<Client> getClient(final @PathVariable Long id) {
    return this.clientService.getClient(id);
  }

  @GetMapping("/get")
  ResponseEntity<List<Client>> getClients() {
    return this.clientService.getClients();
  }
}
