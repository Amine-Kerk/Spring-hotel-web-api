package dev.hotel.exception;

import java.util.List;

public class HotelException extends RuntimeException   {
	
	
	private static final long serialVersionUID = 1944096874941461653L;
	private List<String> messagesErreurs;

	    public HotelException(List<String> messagesErreurs) {
	        this.messagesErreurs = messagesErreurs;
	    }

	    public List<String> getMessagesErreurs() {
	        return messagesErreurs;
	    }


}
