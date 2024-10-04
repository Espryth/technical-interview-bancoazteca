package me.javierca.client.controller;

import com.google.gson.JsonObject;
import me.javierca.client.repository.ClientRepository;
import me.javierca.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  @MockBean
  private ClientRepository clientRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateClient() throws Exception {
    final var client = new JsonObject();
    client.addProperty("name", "Pedro");
    client.addProperty("paternalSurname", "Ramirez");
    client.addProperty("maternalSurname", "Gonzalez");
    client.addProperty("email", "pedro@gmail.com");
    client.addProperty("phone", "1234567890");
    client.addProperty("birthdate", "01/01/2000");
    client.addProperty("gender", "MALE");

    mockMvc
        .perform(
            post("/api/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(client.toString())
        )
        .andDo(print())
        .andExpect(status().isOk());

    client.addProperty("birthdate", "INVALID DATE");

    mockMvc
        .perform(
            post("/api/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(client.toString())
        )
        .andDo(print())
        .andExpect(status().isBadRequest());

    client.addProperty("birthdate", "01/01/2000");
    client.addProperty("phone", "INVALID PHONE");

    mockMvc
        .perform(
            post("/api/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(client.toString())
        )
        .andDo(print())
        .andExpect(status().isBadRequest());

    client.addProperty("phone", "1234567890");
    client.addProperty("email", "INVALID EMAIL");

    mockMvc
        .perform(
            post("/api/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(client.toString())
        )
        .andDo(print())
        .andExpect(status().isBadRequest());

  }
}
