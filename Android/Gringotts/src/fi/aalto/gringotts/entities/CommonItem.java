package fi.aalto.gringotts.entities;

import java.util.Date;

public class CommonItem {
	public String Content = "";
	public Date Time = null;
	public int Ammount = 0;
	public NotificationType Type;
	public String Url = "";

	/**
	 * Construct a feed
	 * @param content
	 * @param time
	 * @param ammount
	 * @param type
	 */
	public CommonItem(String content, Date time, int ammount, NotificationType type) {		
		Content = content;
		Time = time;
		Ammount = ammount;
		Type = type;
	}
	
	/**
	 * Construct a notification
	 * @param content
	 * @param time
	 * @param ammount
	 * @param type
	 * @param imageUrl
	 */
	public CommonItem(String content, Date time, int ammount, NotificationType type, String imageUrl) {		
		Content = content;
		Time = time;
		Ammount = ammount;
		Type = type;
		Url = imageUrl;
	}
	
	/**
	 * Construct a contact
	 * @param content
	 * @param imageUrl
	 */
	public CommonItem(String content, String imageUrl){
		Content = content;
		Url = imageUrl;
	}

}
