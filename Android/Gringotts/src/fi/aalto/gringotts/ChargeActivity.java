package fi.aalto.gringotts;

import java.util.Locale;

import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.PaymentActivity.PlaceholderFragment;
import fi.aalto.gringotts.entities.User;
import fi.aalto.gringotts.fragments.GroupChargeFragment;
import fi.aalto.gringotts.utils.RoundedImageView;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ActionBar;
//import android.app.Fragment;
//import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
//import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class ChargeActivity extends CommonActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge);
		setTitle("New Charge");

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                getActionBar().setSelectedNavigationItem(position);
            }
        });

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab1 = actionBar.newTab().setText("SINGLE");
		Tab tab2 = actionBar.newTab().setText("GROUP");

		tab1.setTabListener(this);
		tab2.setTabListener(this);
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.charge, menu);
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			Log.d("GGEEEEEEET", "" + position);
			switch (position) {
			case 0:
				return new PlaceholderFragment();
			case 1:
				return new GroupChargeFragment();
			}

			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ImageFetcher mImageFetcher;
		private int THUMB_SIZE = 240;
		private RoundedImageView userImage;
		private RoundedImageView targetImage;
		private Button plusButton;
		private TextView targetName;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_charge, container, false);
			mImageFetcher = ImageFetcher.createImageFetcher((FragmentActivity) this.getActivity(), THUMB_SIZE);

			initUi(rootView);
			return rootView;
		}

		private void initUi(View rootView) {
			userImage = (RoundedImageView) rootView.findViewById(R.id.userImage);
			targetImage = (RoundedImageView) rootView.findViewById(R.id.targetImage);

			mImageFetcher.loadImage(Constants.MOCKPICTURE, userImage);
			mImageFetcher.loadImage(Constants.MOCKPICTURE, targetImage);
			
			plusButton = (Button) rootView.findViewById(R.id.plus_button);
			targetName = (TextView) rootView.findViewById(R.id.target_name);
			hideTarget();
			plusButton.setOnClickListener(addContact);
			mImageFetcher.loadImage(Constants.MOCKPICTURE, userImage);
		}

		OnClickListener addContact = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PlaceholderFragment.this.getActivity(), FriendListSelectionActivity.class);
				PlaceholderFragment.this.startActivityForResult(i, 0);
			}
		};

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			User target = (User) data.getSerializableExtra("data");
			setTarget(target);
		}

		public void hideTarget() {
			plusButton.setVisibility(View.VISIBLE);
			targetImage.setVisibility(View.GONE);
			targetName.setVisibility(View.INVISIBLE);
		}

		public void setTarget(User user) {
			plusButton.setVisibility(View.GONE);
			targetImage.setVisibility(View.VISIBLE);
			targetName.setVisibility(View.VISIBLE);
			mImageFetcher.loadImage(user.Image, targetImage);
			targetName.setText(user.Name);
			targetImage.setOnTouchListener(dragOut);
		}

		OnTouchListener dragOut = new OnTouchListener() {
			private Rect rect; // Variable rect to hold the bounds of the view

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// Construct a rect of the view's bounds
					rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
				}

				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
						hideTarget();
					}
				}
				return true;
			}
		};

		@Override
		public void onDestroy() {
			mImageFetcher.clearCache();
			super.onDestroy();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.drawable.pull_in_left, R.drawable.push_out_right);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
		mViewPager.getAdapter().notifyDataSetChanged();

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
