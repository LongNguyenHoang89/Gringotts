package fi.aalto.gringotts.mbdata;

import java.util.List;

import android.util.Log;
import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

public class Operations {
	private static final String TAG = Operations.class.getSimpleName();

	public static void insert(RegistrationID regId) {
		insert2DB(regId);
	}

	public static void delete(RegistrationID regId) {
		deleteFromDB(regId);
	}

	public static void fetchRegistrations(OnCompletion onCompletion) {
		search(RegistrationID.class, onCompletion);
	}

	public static void insert(Account account) {
		insert2DB(account);
	}

	public static void fetchAccounts(OnCompletion onCompletion) {
		search(Account.class, onCompletion);
	}

	public static void insert(Event event) {
		insert2DB(event);
	}

	public static void fetchEvents(OnCompletion onCompletion) {
		search(Event.class, onCompletion);
	}

	public static void insert(Charge charge) {
		insert2DB(charge);
	}

	public static void fetchCharges(OnCompletion onCompletion) {
		search(Charge.class, onCompletion);
	}

	public static void fetchTransactions(OnCompletion onCompletion) {
		search(Transaction.class, onCompletion);
	}

	private static void insert2DB(final IBMDataObject entity) {
		// Use the IBMDataObject to create and persist the object
		entity.save().continueWith(new Continuation<IBMDataObject, Void>() {

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
					Log.d(TAG, "Added entry : " + entity.toString());
				}
				return null;
			}
		});
	}

	private static void search(Class entityClass, final OnCompletion onCompletion) {
		try {
			IBMQuery<IBMDataObject> query = IBMQuery.queryForClass(entityClass);
			// Query all the registration ids from the server
			query.find().continueWith(
					new Continuation<List<IBMDataObject>, Void>() {
						@Override
						public Void then(Task<List<IBMDataObject>> task)
								throws Exception {
							final List<IBMDataObject> objects = task.getResult();
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
								onCompletion.onComplete(objects);								
							}
							return null;
						}
					}, Task.UI_THREAD_EXECUTOR);
		} catch (IBMDataException error) {
			Log.e(TAG, "Exception : " + error.getMessage());
		}
	}

	private static void deleteFromDB(final IBMDataObject entity) {
		// This will attempt to delete the item on the server.
		entity.delete().continueWith(new Continuation<IBMDataObject, Void>() {
			@Override
			public Void then(Task<IBMDataObject> task) throws Exception {
				// Log if the delete was cancelled.
				if (task.isCancelled()) {
					Log.e(TAG, "Exception : Task " + task.toString()
							+ " was cancelled.");
				}
				// Log error message, if the delete task fails.
				else if (task.isFaulted()) {
					Log.e(TAG, "Exception : " + task.getError().getMessage());
				}
				// If the result succeeds, reload the list.
				else {
					Log.d(TAG, "Deleted entity : " + entity.toString());
				}
				return null;
			}
		}, Task.UI_THREAD_EXECUTOR);
	}
}