package fi.aalto.gringotts.mbdata;

import java.util.Date;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * 
 * This class represents DB table, which maps user FB ID to the unique
 * registration ID. Registration ID is nothing but a unique device identifier.
 * It is used for sending push notification to specific device.
 * 
 * 
 */
@IBMDataObjectSpecialization("RegistrationID")
public class RegistrationID extends IBMDataObject {
	private static final String TAG = RegistrationID.class.getSimpleName();
	// DB Columns
	private static final String FB_ID = "FB_ID";
	private static final String REG_ID = "REG_ID";
	private static final String TSMP = "TSMP";

	public String getFacebookId() {
		return (String) getObject(FB_ID);
	}

	public String getRegistrationId() {
		return (String) getObject(REG_ID);
	}

	public long getTimestamp() {
		return (Long) getObject(TSMP);
	}

	public void setFacebookId(String facebookId) {
		setObject(FB_ID, (facebookId != null) ? facebookId : "");
	}

	public void setRegistrationId(String registrationId) {
		setObject(REG_ID, (registrationId != null) ? registrationId : "");
	}

	public void setTimestamp() {
		setObject(TSMP, System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return new Date(getTimestamp()) + " | " + getFacebookId() + " | "
				+ getRegistrationId();
	}
}