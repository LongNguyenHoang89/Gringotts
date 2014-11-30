package fi.aalto.gringotts.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ticket implements Serializable{
	public String id;
	
	public Ticket(String _id) {
		id = _id;
	}
}
