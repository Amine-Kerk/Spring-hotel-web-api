package dev.hotel.webcontrolleur;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
public class ClientController {
	
	private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

	// GET /clients
    @RequestMapping(
            method = RequestMethod.GET,
            path = "clients"
    )
    public List<Client> listerClients(
            @RequestParam Integer start,
            @RequestParam Integer size
    ) {
        return clientRepository.findAll(PageRequest.of(start, size)).getContent();
    }

}
