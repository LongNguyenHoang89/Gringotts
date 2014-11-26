package fi.aalto.gringotts.adapters;

import fi.aalto.gringotts.fragments.EventListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class EventTabsPagerAdapter extends FragmentPagerAdapter {

	public EventTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		EventListFragment fragment = new EventListFragment();
		Bundle args = new Bundle();
		if (index == 0) {
			args.putInt("type", EventListFragment.TYPE_ALL);
		} else if (index == 1) {
			args.putInt("type", EventListFragment.TYPE_HOSTING);
		}
	    
	    fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}
