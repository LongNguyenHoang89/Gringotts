package fi.aalto.gringotts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;

import fi.aalto.gringotts.mbdata.Account;
import fi.aalto.gringotts.mbdata.OnCompletion;
import fi.aalto.gringotts.mbdata.Operations;
import fi.aalto.gringotts.mbdata.RegistrationID;

public class GringottsApplication extends Application {
	private static final String TAG = GringottsApplication.class
			.getSimpleName();

	private String sRoute = "10.100.28.219:3000";
	
	private final String USER_AGENT = "Mozilla/5.0";

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
		// initDB();

		Log.d(TAG, "Reading registration ids");
		// fetchAll();

		// Initialize route
		// sRoute = props.getProperty(Constants.APP_ROUTE);
		Log.d(TAG, "Route " + sRoute);

		//JUST FOR TESTING******************************
		new Thread(new Runnable() {
			public void run() {
				if (transaction("FB_43e26d43-9d01-42bb-ac11-09496a10ed8f",
						"FB_cb74f793-e05a-4864-bd40-3e75fcf202e2", 4.5f,
						"From an android app"))
					Log.d(TAG, "Transaction successfull");
				else
					Log.d(TAG, "Transaction unsuccessfull");
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				if (charge())
					Log.d(TAG, "Charge successfull");
				else
					Log.e(TAG, "Charge unsuccessfull");
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				if (charge("EVENT_7ac87b8e-ecbe-4bf7-b98e-c4f7d118d8cf"))
					Log.d(TAG, "Charge with event id successfull");
				else
					Log.e(TAG, "Charge with event id unsuccessfull");
			}
		}).start();
		//JUST FOR TESTING****************** ************
	}

	private String encode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Ignore
		}
		return "";
	}

	public boolean transaction(String sender, String receiver, float amount,
			String remark) {
		if (sRoute == null) {
			return false;
		}
		String postData = "&sender=" + encode(sender) + "&receiver="
				+ encode(receiver) + "&amount=" + amount + "&remark="
				+ encode(remark);

		HttpURLConnection urlConnection = null;
		BufferedReader buffReader = null;
		URL url;

		try {
			url = new URL("http://" + sRoute + "/transaction");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			initializeURLConnection(urlConnection);

			DataOutputStream wr = new DataOutputStream(
					urlConnection.getOutputStream());
			wr.writeBytes(postData);
			wr.flush();
			wr.close(); 

			int code = urlConnection.getResponseCode();
			if (code == 200) {
				return true;
			}
			Log.e(TAG, "Status code error");
			return false;
		} catch (IOException ioe) {
			Log.e(TAG, ioe.getMessage());
			return false;
		} finally {
			if (buffReader != null) {
				try {
					buffReader.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}
	}

	public boolean charge() {
		if (sRoute == null) {
			return false;
		}
		return getRequest("http://" + sRoute + "/charge/");
	}

	public boolean charge(String eventId) {
		if (sRoute == null) {
			return false;
		}
		return getRequest("http://" + sRoute + "/charge/" + eventId);
	}

	public boolean balance(String facebookId) {
		if (sRoute == null) {
			return false;
		}
		return getRequest("http://" + sRoute + "/balance/" + facebookId);
	}

	public boolean getRequest(String strURL) {
		HttpURLConnection urlConnection = null;
		URL url;
		try {
			url = new URL(strURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("User-Agent", USER_AGENT);

			int code = urlConnection.getResponseCode();
			if (code == 200) {
				return true;
			}
			Log.e(TAG, "Status code error");
			return false;
		} catch (IOException ioe) {
			Log.e(TAG, ioe.getMessage());
			return false;
		}
	}

	private void initializeURLConnection(HttpURLConnection urlConnection) {
		urlConnection
				.setRequestProperty("User-Agent",
						"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:30.0) Gecko/20100101 Firefox/30.0");
		urlConnection
				.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		urlConnection.setRequestProperty("Accept-Encoding", "deflate");
		urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setDoOutput(true);
	}

	private void initDB() {
		/*
		 * final RegistrationID regId1 = new RegistrationID();
		 * regId1.setFacebookId("FB_" + uuid());
		 * regId1.setRegistrationId("REG_ID_" + uuid()); regId1.setTimestamp();
		 * Operations.insert(regId1);
		 * 
		 * final RegistrationID regId2 = new RegistrationID();
		 * regId2.setFacebookId("FB_" + uuid());
		 * regId2.setRegistrationId("REG_ID_" + uuid()); regId2.setTimestamp();
		 * Operations.insert(regId2);
		 * 
		 * final RegistrationID regId3 = new RegistrationID();
		 * regId3.setFacebookId("FB_" + uuid());
		 * regId3.setRegistrationId("REG_ID_" + uuid()); regId3.setTimestamp();
		 * Operations.insert(regId3);
		 * 
		 * Event event = new Event(); event.setId("EVENT_" + uuid());
		 * event.setDetail("Event details");
		 * event.setOrganizerFBId(regId1.getFacebookId()); event.setTimestamp();
		 * Operations.insert(event);
		 * 
		 * Charge charge1 = new Charge(); charge1.setEventId(event.getId());
		 * charge1.setAmount(78.78f);
		 * charge1.setPaidByFbId(regId2.getFacebookId());
		 * charge1.setStatus(PaymentStatus.OPEN); Operations.insert(charge1);
		 * 
		 * Charge charge2 = new Charge(); charge2.setEventId(event.getId());
		 * charge2.setAmount(99.78f);
		 * charge2.setPaidByFbId(regId3.getFacebookId());
		 * charge2.setStatus(PaymentStatus.OPEN); Operations.insert(charge2);
		 */

		Account account1 = new Account();
		account1.setFacebookId("FB_cb74f793-e05a-4864-bd40-3e75fcf202e2");
		account1.setIBAN("13");
		account1.setName("Name 1");
		account1.setRemark("Remark 1");
		account1.setTimestamp();
		account1.setAddress("Address 1");
		Operations.insert(account1);

		Account account2 = new Account();
		account2.setFacebookId("FB_43e26d43-9d01-42bb-ac11-09496a10ed8f");
		account2.setIBAN("21");
		account2.setName("Name 2");
		account2.setRemark("Remark 2");
		account2.setTimestamp();
		account2.setAddress("Address 2");
		Operations.insert(account2);

		/*
		 * Account account3 = new Account();
		 * account3.setFacebookId(regId3.getFacebookId());
		 * account3.setIBAN("IBAN_" + uuid()); account3.setName("Name 3");
		 * account3.setRemark("Remark 3"); account3.setTimestamp();
		 * account3.setAddress("Address 3"); Operations.insert(account3);
		 */
	}

	private void fetchAll() {
		Operations.fetchRegistrations(new OnCompletion());
		// Operations.fetchAccounts();
		Operations.fetchEvents(new OnCompletion());
		Operations.fetchCharges(new OnCompletion());
	}

	private String uuid() {
		return UUID.randomUUID().toString();
	}
}