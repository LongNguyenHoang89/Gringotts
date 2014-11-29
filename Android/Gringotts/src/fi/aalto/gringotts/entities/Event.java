package fi.aalto.gringotts.entities;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Event implements Serializable{
	public String name;
	public String url;
	public boolean isHost;
	public boolean sellingTicket;
	public double ticketPrice;
	public Ticket ownTicket = null;
	public String date;
	public String location;
	public List<String> guests;
	
	public Event(String _name, String _url) {
		name = _name;
		url = _url;
	}
}
