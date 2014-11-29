package fi.aalto.gringotts;

import fi.aalto.gringotts.adapters.EventTabsPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;

public class EventList extends CommonActivity implements ActionBar.TabListener {

	private ViewPager mViewPager;
	private EventTabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private static final String[] TABS = { "All", "Hosting" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		setTitle("Events");
		// Initilization
		mViewPager = (ViewPager) findViewById(R.id.event_pager);
		actionBar = getActionBar();
		mAdapter = new EventTabsPagerAdapter(getFragmentManager());

		mViewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : TABS) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.drawable.pull_in_left,
				R.drawable.push_out_right);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
		mViewPager.getAdapter().notifyDataSetChanged();
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}

}
