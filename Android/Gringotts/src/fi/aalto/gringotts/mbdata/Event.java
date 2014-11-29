package fi.aalto.gringotts.mbdata;

import java.util.Date;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * 
 * This class represents DB table Event. Event is fetched from the Facebook and
 * stored.
 * 
 */
@IBMDataObjectSpecialization("Event")
public class Event extends IBMDataObject {
	private static final String TAG = Event.class.getSimpleName();

	// DB Columns
	private static final String ID = "ID";
	private static final String DETAIL = "DETAIL";
	private static final String ORGANIZER_FB_ID = "ORGANIZER_FB_ID";
	private static final String TSMP = "TSMP";

	public String getId() {
		return (String) getObject(ID);
	}

	public String getDetail() {
		return (String) getObject(DETAIL);
	}

	public String getOrganizerFBId() {
		return (String) getObject(ORGANIZER_FB_ID);
	}

	public long getTimestamp() {
		return (Long) getObject(TSMP);
	}

	public void setId(String id) {
		setObject(ID, (id != null) ? id : "");
	}

	public void setDetail(String detail) {
		setObject(DETAIL, (detail != null) ? detail : "");
	}

	public void setOrganizerFBId(String orgnizerFBId) {
		setObject(ORGANIZER_FB_ID, (orgnizerFBId != null) ? orgnizerFBId : "");
	}

	public void setTimestamp() {
		setObject(TSMP, System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return "Event : " + new Date(getTimestamp()) + " | " + getId() + " | " + getDetail()
				+ " | " + getOrganizerFBId();
	}
}