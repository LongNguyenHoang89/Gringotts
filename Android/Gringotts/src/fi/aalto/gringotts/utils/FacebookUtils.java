package fi.aalto.gringotts.utils;

import java.util.List;

import android.util.Log;

import com.restfb.*;
import com.restfb.types.Event;

public class FacebookUtils {
	private static final String TOKEN = "CAACEdEose0cBAPz28w46RyjNtzEZAl25PGTVgKlIXZC9b4WidF64omiuwZBRdI2SR1fTYtbSZCCZAH7RTJ7n3VIgin9lPXOw6UuhntsH6njsQreKjAqJQPPPdsKEdmNtoYQjKJHglsidzfaI5Ooig15ZBCtPhlgGOAI2s0aacSlIk60G1xZCzysmhFHGdNu7bxqzua2lUBKOBFKBFzz4Fw6";
	
	private FacebookClient facebookClient;

	public FacebookUtils() {
		facebookClient = new DefaultFacebookClient(TOKEN);
		
	}
	
	public List<Event> getHostingEvents() {
		Connection<Event> events = facebookClient.fetchConnection("me/events/created", Event.class);
		
		List<Event> eventList = events.getData();
		int nEvent = eventList.size();
		Log.d("TAG", "Size: " + nEvent);
		for (int i = 0; i < eventList.size(); i++) {
			Log.d("TAG", eventList.get(i) + "");
		}
		
		return eventList;
	}
	
}
