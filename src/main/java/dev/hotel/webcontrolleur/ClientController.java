package dev.hotel.webcontrolleur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;
import dev.hotel.service.ClientService;

@RestController
public class ClientController {
	
	private ClientService clientService ;

	

	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}


	// GET /clients
    @RequestMapping(method = RequestMethod.GET,path = "clients")
    
    public List<Client> listerClients( @RequestParam Integer start, @RequestParam Integer size) {
        return clientService.listerClients(start, size);
    }
    
    
    //GET /clients/UUID
    @RequestMapping(method = RequestMethod.GET,path = "clients/{uuid}")
    
    public  ResponseEntity<?> ClientUUID(@PathVariable UUID uuid ) {
    	Optional<Client> optClient = this.clientService.recupererClient(uuid);
    	
    	if(optClient.isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK).body(optClient);
   	} else {
   	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("l'uuid ne correspond a aucun client en base de données !");
    }
    
    }
    

}
