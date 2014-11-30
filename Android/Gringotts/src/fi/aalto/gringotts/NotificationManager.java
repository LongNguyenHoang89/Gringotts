package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fi.aalto.gringotts.entities.Notification;
import fi.aalto.gringotts.entities.NotificationType;
import fi.aalto.gringotts.entities.UserList;
import fi.aalto.gringotts.utils.UIHelper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Too lazy to do Database, use shared preference then
 * 
 * @author eledra
 *
 */
public class NotificationManager {

	// Constant
	private static final String NOTIFICATION_PREFERENCE = "notification";

	private static NotificationManager INSTANCE;
	private SharedPreferences mPrefs;
	private Editor prefsEditor;

	private ArrayList<Notification> notificationList = new ArrayList<Notification>();
	private Gson converter;
	private CommonActivity activity;
	private Random random;

	public static NotificationManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NotificationManager();
		}
		return INSTANCE;
	}

	private NotificationManager() {
		notificationList = new ArrayList<Notification>();
		converter = new Gson();
		random = new Random();
	}

	public void Init(SharedPreferences sp, CommonActivity act) {
		mPrefs = sp;
		activity = act;
		prefsEditor = sp.edit();
		String current = mPrefs.getString(NOTIFICATION_PREFERENCE, "");
		if (current != "") {
			// Get the list from preferences
			notificationList = converter.fromJson(current, new TypeToken<ArrayList<Notification>>() {
			}.getType());
		}
		UpdateNotification();
	}

	public void UpdateNotification() {
		UIHelper.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int number = NotificationManager.this.notificationList.size();
				activity.showNotification(number);
			}
		});
	}

	public void AddNewNotification(String message) {
		NotificationMessage ms = converter.fromJson(message, NotificationMessage.class);
		Notification ntf = new Notification();
		ntf.amount = ms.Amount;
		ntf.Content = ms.Message;
		ntf.DateTime = new Date();
		ntf.Issuer = UserList.getInstance().getUser(ms.Source);
		ntf.Target = UserList.getInstance().getUser(ms.Target);

		if (ms.Type.equals("charge")) {
			ntf.Type = NotificationType.CHARGE;
		} else {
			ntf.Type = NotificationType.PAID;
		}

		ntf.IsNew = true;
		ntf.Id = String.valueOf(random.nextLong());

		notificationList.add(ntf);
		prefsEditor.putString(NOTIFICATION_PREFERENCE, converter.toJson(notificationList));
		prefsEditor.commit();

		UpdateNotification();
	}

	public void NotificationConfirm(Notification newNote) {
		for (int i = 0; i < notificationList.size(); i++) {
			if (notificationList.get(i).Id.equals(newNote.Id)) {
				notificationList.get(i).IsNew = false;
				prefsEditor.putString(NOTIFICATION_PREFERENCE, converter.toJson(notificationList));
				prefsEditor.commit();
			}
		}
	}

	/**
	 * use this to parse to GSON
	 * 
	 * @author eledra
	 *
	 */
	public class NotificationMessage {
		String Type;
		String Source;
		String Target;
		double Amount;
		String Message;
	}
}
