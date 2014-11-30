package fi.aalto.gringotts.utils;

import android.os.Handler;
import android.os.Looper;

public class UIHelper {
	public static void runOnUiThread(Runnable runnable) {
		final Handler UIHandler = new Handler(Looper.getMainLooper());
		UIHandler.post(runnable);
	}
}
