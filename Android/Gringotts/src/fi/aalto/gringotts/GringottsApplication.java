package fi.aalto.gringotts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;

import fi.aalto.gringotts.mbdata.Charge;
import fi.aalto.gringotts.mbdata.Event;
import fi.aalto.gringotts.mbdata.Operations;
import fi.aalto.gringotts.mbdata.PaymentStatus;
import fi.aalto.gringotts.mbdata.RegistrationID;

public class GringottsApplication extends Application {

	private static final String TAG = GringottsApplication.class
			.getSimpleName();

	public GringottsApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initializeBluemixApp();
	}

	private void initializeBluemixApp() {
		// Read from properties file, where bluemix application details are
		// stored
		Properties props = new java.util.Properties();
		Context context = getApplicationContext();
		try {
			AssetManager assetManager = context.getAssets();
			props.load(assetManager.open(Constants.PROPS_FILE));
			Log.i(TAG, "Found configuration file: " + Constants.PROPS_FILE);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "The bluelist.properties file was not found.", e);
		} catch (IOException e) {
			Log.e(TAG,
					"The bluelist.properties file could not be read properly.",
					e);
		}
		// initialize the IBM core backend-as-a-service
		IBMBluemix.initialize(this, props.getProperty(Constants.APP_ID),
				props.getProperty(Constants.APP_SECRET),
				props.getProperty(Constants.APP_ROUTE));
		// initialize the IBM Data Service
		IBMData.initializeService();
		// register the Registration ID
		RegistrationID.registerSpecialization(RegistrationID.class);

		Log.d(TAG, "Inserting dummy records");
		initDB();

		Log.d(TAG, "Reading registration ids");
		fetchAll();
	}

	private void initDB() {
		final RegistrationID regId1 = new RegistrationID();
		regId1.setFacebookId("FB_" + uuid());
		regId1.setRegistrationId("REG_ID_" + uuid());
		regId1.setTimestamp();
		Operations.insert(regId1);

		final RegistrationID regId2 = new RegistrationID();
		regId2.setFacebookId("FB_" + uuid());
		regId2.setRegistrationId("REG_ID_" + uuid());
		regId2.setTimestamp();
		Operations.insert(regId2);

		final RegistrationID regId3 = new RegistrationID();
		regId3.setFacebookId("FB_" + uuid());
		regId3.setRegistrationId("REG_ID_" + uuid());
		regId3.setTimestamp();
		Operations.insert(regId3);

		Event event = new Event();
		event.setId("EVENT_" + uuid());
		event.setDetail("Event details");
		event.setOrganizerFBId(regId1.getFacebookId());
		event.setTimestamp();
		Operations.insert(event);

		Charge charge1 = new Charge();
		charge1.setEventId(event.getId());
		charge1.setAmount(78.78f);
		charge1.setPaidByFbId(regId2.getFacebookId());
		charge1.setStatus(PaymentStatus.OPEN);
		Operations.insert(charge1);

		Charge charge2 = new Charge();
		charge2.setEventId(event.getId());
		charge2.setAmount(99.78f);
		charge2.setPaidByFbId(regId3.getFacebookId());
		charge2.setStatus(PaymentStatus.OPEN);
		Operations.insert(charge2);

	}

	private void fetchAll() {
		Operations.fetchRegistrations();
		// Operations.fetchAccounts();
		Operations.fetchEvents();
		Operations.fetchCharges();
	}

	private String uuid() {
		return UUID.randomUUID().toString();
	}
}