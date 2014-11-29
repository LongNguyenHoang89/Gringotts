package fi.aalto.gringotts;

import fi.aalto.gringotts.entities.Event;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class GroupChargeActivity extends CommonActivity {
	public static final int TYPE_CHARGE = 1;
	public static final int TYPE_DONATION = 2;
	
	private Event mEvent = null;
	private int mType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_charge);
		
		Intent i = getIntent();
		mType = i.getExtras().getInt("type", 1);
		mEvent = (Event) i.getSerializableExtra("eventInfo");
		
		// set title
		setTitle(mEvent.name);
		
		// set type DONATION or CHARGE
		if (mType == TYPE_DONATION) {
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_charge, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
