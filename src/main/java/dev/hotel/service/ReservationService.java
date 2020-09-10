package dev.hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.HotelException;
import dev.hotel.repository.ReservationRepository;



@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private ClientService clientService;
	private ChambreService chambreService;
	
	

	public ReservationService(ReservationRepository reservationRepository, ClientService clientService,
			ChambreService chambreService) {
		
		this.reservationRepository = reservationRepository;
		this.clientService = clientService;
		this.chambreService = chambreService;
	}

	public List<Reservation> ListerReservations(int numeroPage,int taille){
		return reservationRepository.findAll(PageRequest.of(numeroPage, taille)).getContent();
	}
	
	@Transactional
    public Reservation CreerNewReservation(LocalDate dateDebut, LocalDate dateFin, UUID clientId,List<UUID>chambres) {
		
		
		List<String> messagesErreurs = new ArrayList<>();

		Optional<Client> opClient = clientService.recupererClient(clientId);
		
	
		if (!opClient.isPresent()) {
			messagesErreurs.add("L'uuid " + clientId + " ne correspond à aucun client");
		}
		
		
		
		
		
		List<Chambre> listChambres = new ArrayList<>();

		for (UUID uuidChambre : chambres) {
			Optional<Chambre> optionalChambre = chambreService.recupererChambre(uuidChambre);
			if (optionalChambre.isPresent()) {
				listChambres.add(optionalChambre.get());
			} else {
				messagesErreurs.add("L'uuid " + uuidChambre + " ne correspond à aucune chambre");
			}
		}
		
		
		if (!messagesErreurs.isEmpty()) {
			throw new HotelException(messagesErreurs);
		}

		
		Reservation reservation = new Reservation() ;
		
		
		reservation.setDateDebut(dateDebut);
		reservation.setDateFin(dateFin);
		reservation.setClient(opClient.get());
		reservation.setChambres(listChambres);

		return reservationRepository.save(reservation);


		
		

    }

	
	
}
