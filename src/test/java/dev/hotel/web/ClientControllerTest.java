package dev.hotel.web;

import dev.hotel.entite.Client;

import dev.hotel.service.ClientService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)

@WebMvcTest(ClientController.class) // contrôleur à tester
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
  

    @MockBean
   private  ClientService clientService;

    @Test
    public void testListerClientsAvec2Clients() throws Exception {
        //
        Client c1 = new Client();
        c1.setNom("Boop");
        c1.setPrenoms("Betty");

        Client c2 = new Client();
        c2.setNom("Rabbit");
        c2.setPrenoms("Roger");

Mockito.when(clientService.listerClients(0,2)).thenReturn(Arrays.asList(c1,c2));




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
        mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=2"))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].nom").value("Boop"))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].prenoms").value("Betty"))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].nom").value("Rabbit"))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].prenoms").value("Roger"));
            
    }
    
    
    
    @Test
    public void TestClientUUID() throws Exception {
    	Client client = new Client ("Boop","Betty");
    	UUID id = UUID.randomUUID();
    	client.setUuid(id);
    	
    	Mockito.when(clientService.recupererClient(id)).thenReturn(Optional.of(client));
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/clients/{uuid}", id))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.nom").value("Boop"))
    	.andExpect(jsonPath("$.prenoms").value("Betty"));
    	
    }
    
    
    
    @Test
    public void TestCreerClient() throws Exception {
    	Client client = new Client();
    	client.setNom("bob");
    	client.setPrenoms("eponge");
    	
    	Mockito.when(clientService.creerNouveauClient("lolo", "bob")).thenReturn(client);
    	
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/clients")
    	           .contentType(MediaType.APPLICATION_JSON)
    	           .content("{\"nom\": \"lolo\", \"prenoms\": \"bob\" }") 
    	           .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
     }
    
}

