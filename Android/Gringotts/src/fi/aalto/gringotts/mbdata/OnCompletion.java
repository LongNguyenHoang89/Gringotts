package fi.aalto.gringotts.mbdata;

import java.util.List;

import android.util.Log;

import com.ibm.mobile.services.data.IBMDataObject;

public class OnCompletion {
	private static final String TAG = OnCompletion.class.getSimpleName();

	public void onComplete(List<IBMDataObject> objects) {
		// If the result succeeds, log the entities.
		for (IBMDataObject entity : objects) {
			Log.d(TAG, "Fetched entity " + entity.toString());
		}
	}
}
