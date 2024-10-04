package me.javierca.invoice.client;

import me.javierca.invoice.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client", url = "http://localhost:8080")
public interface ClientFeignClient {

  @GetMapping("/api/clients/get/{id}")
  ClientDTO getClient(final @PathVariable Long id);

}
