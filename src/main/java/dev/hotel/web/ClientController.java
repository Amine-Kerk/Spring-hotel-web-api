package dev.hotel.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;

import dev.hotel.service.ClientService;
import dev.hotel.web.client.CreerClientRequestDto;
import dev.hotel.web.client.CreerClientResponseDto;

@RestController
@RequestMapping("clients")   //nous permet d'eviter d'appeler le @RequestMapping de clients dans les methodes 
public class ClientController {
	
	private ClientService clientService ;

	

	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}


	// GET /clients
    //@RequestMapping(method = RequestMethod.GET,path = "clients")
    @GetMapping
    public List<Client> listerClients( @RequestParam Integer start, @RequestParam Integer size) {
        return clientService.listerClients(start, size);
    }
    
    
    //GET /clients/UUID
    //@RequestMapping(method = RequestMethod.GET,path = "clients/{uuid}")
    @GetMapping("{uuid}")
    public  ResponseEntity<?> ClientUUID(@PathVariable UUID uuid ) {
    	Optional<Client> optClient = this.clientService.recupererClient(uuid);
    	
    	if(optClient.isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK).body(optClient);
   	} else {
   	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("l'uuid ne correspond a aucun client en base de données !");
    }
    
    }
    @PostMapping //ajout de lapi post pour creer un nouveau client 
	public ResponseEntity<?> creerClient(@RequestBody @Valid CreerClientRequestDto client, BindingResult resultatValidation) {

		if (resultatValidation.hasErrors()) {
			return ResponseEntity.badRequest().body("Erreur");
		}

		return ResponseEntity.ok(new CreerClientResponseDto(clientService.creerNouveauClient(client.getNom(), client.getPrenoms())));
	}

    

}
