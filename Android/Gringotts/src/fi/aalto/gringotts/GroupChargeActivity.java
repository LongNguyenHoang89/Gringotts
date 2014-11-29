package fi.aalto.gringotts;

import fi.aalto.gringotts.entities.Event;
import fi.aalto.gringotts.fragments.GroupChargeFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GroupChargeActivity extends CommonActivity {
	private Event mEvent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_charge);
		
		if (savedInstanceState == null) {
			Intent i = getIntent();
			mEvent = (Event) i.getSerializableExtra("eventInfo");
			
			// set title
			setTitle(mEvent.name);
			
			GroupChargeFragment fragment = new GroupChargeFragment();
			Bundle args = new Bundle();
			args.putSerializable("eventInfo", mEvent);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
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
