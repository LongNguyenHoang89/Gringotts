package fi.aalto.gringotts.entity;

import java.util.Date;

public class Notification {
	public String Content;
	public Date Time;
	public int Ammount;
	public NotificationType Type;

	public Notification(String content, Date time, int ammount, NotificationType type) {
		super();
		Content = content;
		Time = time;
		Ammount = ammount;
		Type = type;
	}

}
