package fi.aalto.gringotts;

import android.app.ActionBar;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateActionbar();
		backButton = ((ImageButton) findViewById(R.id.back_button));
		backButton.setOnClickListener(backClick);
		homeButton = ((ImageButton) findViewById(R.id.home_button));
		homeButton.setOnClickListener(homeClick);
	}

	OnClickListener backClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onBackPressed();
			overridePendingTransition(R.drawable.pull_in_left, R.drawable.push_out_right);
		}
	};

	OnClickListener homeClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(CommonActivity.this, MainActivity.class);
			startActivity(i);
			overridePendingTransition(R.drawable.pull_in_left, R.drawable.push_out_right);
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

	protected void setTitle(String text) {
		TextView label = (TextView) findViewById(R.id.screen_title);
		label.setText("PopCoin");
	}
}
