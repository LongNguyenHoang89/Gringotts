package fi.aalto.gringotts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import fi.aalto.gringotts.mbdata.Operations;
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

		Log.d(TAG, "Inserting registration ids");
		insertRegistrationId();
		insertRegistrationId();
		insertRegistrationId();

		Log.d(TAG, "Reading registration ids");
		getRegistrationIds();
	}

	private void insertRegistrationId() {
		final RegistrationID regId = new RegistrationID();
		regId.setFacebookId("FB_" + uuid());
		regId.setRegistrationId("REG_ID_" + uuid());
		regId.setTimestamp();

		Operations.insert(regId);

		/*// Use the IBMDataObject to create and persist the RegistrationID
		// object.
		regId.save().continueWith(new Continuation<IBMDataObject, Void>() {

			@Override
			public Void then(Task<IBMDataObject> task) throws Exception {
				// Log if the save was cancelled.
				if (task.isCancelled()) {
					Log.e(TAG, "Exception : Task " + task.toString()
							+ " was cancelled.");
				} else if (task.isFaulted()) {
					// Log error message, if the save task fails.
					Log.e(TAG, "Exception : " + task.getError().getMessage());
				} else {
					// If the result succeeds, log the added entry.
					Log.d(TAG, "Added entry : " + regId.toString());
				}
				return null;
			}
		});*/
	}

	private void getRegistrationIds() {/*
		try {
			IBMQuery<RegistrationID> query = IBMQuery
					.queryForClass(RegistrationID.class);
			// Query all the registration ids from the server
			query.find().continueWith(
					new Continuation<List<RegistrationID>, Void>() {

						@Override
						public Void then(Task<List<RegistrationID>> task)
								throws Exception {
							final List<RegistrationID> objects = task.getResult();
							// Log if the find was cancelled.
							if (task.isCancelled()) {
								Log.e(TAG,
										"Exception : Task " + task.toString()
												+ " was cancelled.");
							} else if (task.isFaulted()) {
								// Log error message, if the find task fails.
								Log.e(TAG, "Exception : "
										+ task.getError().getMessage());
							} else {
								// If the result succeeds, load the list.
								for (IBMDataObject regId : objects) {
									Log.d(TAG,
											"Fetched registration ID "
													+ ((RegistrationID) regId)
															.toString());
								}
							}
							return null;
						}
					}, Task.UI_THREAD_EXECUTOR);

		} catch (IBMDataException error) {
			Log.e(TAG, "Exception : " + error.getMessage());
		}
	*/
		Operations.fetchRegistrationIds();	
	}

	private String uuid() {
		return UUID.randomUUID().toString();
	}
}