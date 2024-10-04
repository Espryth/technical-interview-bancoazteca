package me.javierca.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ClientApplication.class, args);
  }
}
