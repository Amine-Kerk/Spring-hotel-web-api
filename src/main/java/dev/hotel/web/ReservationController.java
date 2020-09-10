package dev.hotel.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Reservation;
import dev.hotel.exception.HotelException;
import dev.hotel.service.ReservationService;
import dev.hotel.web.reservation.CreerReservationRequestDto;
import dev.hotel.web.reservation.CreerReservationResponseDto;

@RestController
@RequestMapping("reservations")


public class ReservationController {
	
	 private  ReservationService reservationService;
	 

	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}
	
	
	

	@RequestMapping(method = RequestMethod.GET,path ="reservations")
	public List<Reservation> listerReservation(@RequestParam int start , @RequestParam int size){
		return reservationService.ListerReservations(start, size);
	}
	
	
	
	@PostMapping
	public ResponseEntity<?> reservations(@RequestBody @Valid CreerReservationRequestDto reservation, BindingResult resValid) {

		if (!resValid.hasErrors()) {
			
			Reservation reservationCree = reservationService.CreerNewReservation( 
					reservation.getDateDebut(),
					reservation.getDateFin(),
					reservation.getClientId(),
					reservation.getChambres());
			
			
			CreerReservationResponseDto reservationResponse = new CreerReservationResponseDto();

			return ResponseEntity.ok(reservationResponse);
		} else {
			return ResponseEntity.badRequest().body(" Tous les champs sont obligatoires !");
		}

	}
	
	
	@ExceptionHandler(HotelException.class)
	public ResponseEntity<List<String>> onHotelException(HotelException ex) {
		return ResponseEntity.badRequest().body(ex.getMessagesErreurs());
	}


	
	 
	 
	 
	 
	 
	 
	 
}
