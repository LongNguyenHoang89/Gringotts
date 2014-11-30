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
import android.os.AsyncTask;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;

import fi.aalto.gringotts.mbdata.Account;
import fi.aalto.gringotts.mbdata.Charge;
import fi.aalto.gringotts.mbdata.Event;
import fi.aalto.gringotts.mbdata.Operations;
import fi.aalto.gringotts.mbdata.PaymentStatus;
import fi.aalto.gringotts.mbdata.RegistrationID;

public class GringottsApplication extends Application {
	private static final String TAG = GringottsApplication.class
			.getSimpleName();	
	private final String USER_AGENT = "Mozilla/5.0";

	//private String sRoute = "10.100.28.219:3000";	
	private String sRoute = null;

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

		//Log.d(TAG, "Inserting dummy records");
		//initDB();

		// Log.d(TAG, "Reading registration ids");
		// fetchAll();

		// Initialize route
		sRoute = props.getProperty(Constants.APP_ROUTE);
		Log.d(TAG, "Route " + sRoute);
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
		final RegistrationID regId1 = new RegistrationID();
		regId1.setFacebookId("1");
		regId1.setRegistrationId("547a5e821fde007c80223a02");
		regId1.setTimestamp();
		Operations.insert(regId1);

		final RegistrationID regId2 = new RegistrationID();
		regId2.setFacebookId("2");
		regId2.setRegistrationId("547a787e1fde007c8022accb");
		regId2.setTimestamp();
		Operations.insert(regId2);

		final RegistrationID regId3 = new RegistrationID();
		regId3.setFacebookId("3");
		regId3.setRegistrationId("5479d0641fde007c801eeebc");
		regId3.setTimestamp();
		Operations.insert(regId3);

		final Account account1 = new Account();
		account1.setFacebookId(regId1.getFacebookId());
		account1.setIBAN("13");
		account1.setName("Thanh");
		account1.setAddress("ho chi minh city");
		account1.setRemark("Masters Student, Aalto");
		account1.setTimestamp();
		Operations.insert(account1);

		final Account account2 = new Account();
		account2.setFacebookId(regId2.getFacebookId());
		account2.setIBAN("21");
		account2.setName("Long");
		account2.setAddress("Hanoi");
		account2.setRemark("PhD Student, Aalto");
		account2.setTimestamp();
		Operations.insert(account2);

		final Account account3 = new Account();
		account3.setFacebookId(regId3.getFacebookId());
		account3.setIBAN("17");
		account3.setName("Ha");
		account3.setAddress("Hanoi");
		account3.setRemark("Business Student, Aalto");
		account3.setTimestamp();
		Operations.insert(account3);

		Event event = new Event();
		event.setId("65");
		event.setDetail("Party at Ha's place");
		event.setOrganizerFBId(regId1.getFacebookId());
		event.setTimestamp();
		Operations.insert(event);

		Charge charge1 = new Charge();
		charge1.setEventId(event.getId());
		charge1.setPaidByFbId(regId2.getFacebookId());
		charge1.setStatus(PaymentStatus.OPEN);
		charge1.setAmount(6.7f);
		Operations.insert(charge1);

		Charge charge2 = new Charge();
		charge2.setEventId(event.getId());
		charge2.setPaidByFbId(regId3.getFacebookId());
		charge2.setStatus(PaymentStatus.OPEN);
		charge2.setAmount(7.2f);
		Operations.insert(charge2);
	}

	private String uuid() {
		return UUID.randomUUID().toString();
	}

	public class TransactionTask extends AsyncTask<Integer, Integer, Integer> {
		private final String sSender;
		private final String sReceiver;
		private final float sAmount;
		private final String sRemark;

		public TransactionTask(String sender, String receiver, float amount,
				String remark) {
			super();
			this.sSender = sender;
			this.sReceiver = receiver;
			this.sAmount = amount;
			this.sRemark = remark;
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			transaction(sSender, sReceiver, sAmount, sRemark);
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}
}