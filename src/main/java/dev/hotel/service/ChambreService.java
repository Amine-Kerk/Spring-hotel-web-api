package dev.hotel.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.hotel.entite.Chambre;
import dev.hotel.repository.ChambreRepository;



@Service
public class ChambreService {

	
	private ChambreRepository chambreRepository ;

	public ChambreService(ChambreRepository chambreRepository) {
		super();
		this.chambreRepository = chambreRepository;
	}
	
	public Optional<Chambre> recupererChambre (UUID uuid) {
		return chambreRepository.findById(uuid);
	}
}
