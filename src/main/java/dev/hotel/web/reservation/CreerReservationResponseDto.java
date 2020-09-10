package dev.hotel.web.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import dev.hotel.entite.Reservation;

public class CreerReservationResponseDto extends CreerReservationRequestDto {
	
	
	private UUID uuid;
	
	

	

	public void CreerReservationReponseDto(Reservation reservation) {
		this.uuid = reservation.getUuid();
		this.setDateDebut(reservation.getDateDebut());
		this.setDateFin(reservation.getDateFin());

	
		List<UUID> chambres = new ArrayList<>();
	
		for (int i = 0; i < reservation.getChambres().size(); i++) {
			chambres.add(reservation.getChambres().get(i).getUuid());
		}
		this.setChambres(chambres);
		this.setClientId(reservation.getClient().getUuid());

	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	

    

}