package dev.hotel.webcontrolleur;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@WebMvcTest(ClientController.class) // contrôleur à tester
public class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientRepository clientRepository;

    @Test
    public void testListerClientsAvec2Clients() throws Exception {
        //
        Client c1 = new Client();
        c1.setNom("Nom 1");
        c1.setPrenoms("Prénoms 1");

        Client c2 = new Client();
        c2.setNom("Nom 2");
        c2.setPrenoms("Prénoms 2");

        when(clientRepository.findAll(PageRequest.of(10, 20)))
                .thenReturn(new PageImpl<>(Arrays.asList(c1, c2)));

        /*
        [
            {
                "uuid": "xxxx",
                "nom": "Nom 1",
                "prenoms" : "Prénoms 1"
            },
            {
                "uuid": "yyyy",
                "nom": "Nom 2",
                "prenoms" : "Prénoms 2"
            }
        ]
         */

        // GET /clients
        mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=10&size=20"))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].nom").value("Nom 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].prenoms").value("Prénoms 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].nom").value("Nom 2"));
    }

}

