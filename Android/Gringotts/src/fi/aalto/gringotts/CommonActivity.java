package fi.aalto.gringotts;

import com.facebook.Session;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CommonActivity extends FragmentActivity {
	ImageButton backButton;
	ImageButton homeButton;
	ImageButton notificationButton;
	TextView notificationNumberView;

	protected FragmentActivity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		populateActionbar();
		backButton = ((ImageButton) findViewById(R.id.back_button));
		backButton.setOnClickListener(backClick);
		homeButton = ((ImageButton) findViewById(R.id.home_button));
		homeButton.setOnClickListener(homeClick);
		notificationButton = ((ImageButton) findViewById(R.id.notification_button));
		notificationNumberView = (TextView) findViewById(R.id.notification_number);
		mActivity = this;
	}

	protected boolean isLoggedInFacebook() {
		Session session = Session.getActiveSession();
		return (session != null && session.isOpened());
	}

	OnClickListener backClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onBackPressed();
			overridePendingTransition(R.drawable.pull_in_left,
					R.drawable.push_out_right);
		}
	};

	public void showNotification(int number) {
		if (number != 0) {
			notificationNumberView.setVisibility(View.VISIBLE);
			notificationNumberView.setText(number);
		} else {
			notificationNumberView.setVisibility(View.INVISIBLE);
		}
	}

	OnClickListener homeClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(CommonActivity.this, MainActivity.class);
			startActivity(i);
			overridePendingTransition(R.drawable.pull_in_left,
					R.drawable.push_out_right);
		}
	};

	protected void populateActionbar() {
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.custom_action_bar);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setIcon(new ColorDrawable(Color.TRANSPARENT));
	}

	protected void hideActionBarIcon() {
		backButton.setVisibility(View.GONE);
		homeButton.setVisibility(View.GONE);
	}

	protected void hideActionBar() {
		getActionBar().hide();
	}

	protected void setTitle(String text) {
		TextView label = (TextView) findViewById(R.id.screen_title);
		label.setText(text);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.drawable.pull_in_right,
				R.drawable.push_out_left);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.drawable.pull_in_left,
				R.drawable.push_out_right);
	}
}
