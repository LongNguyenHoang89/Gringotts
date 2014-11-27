package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapters.CommonListAdapter;
import fi.aalto.gringotts.entities.CommonItem;
import fi.aalto.gringotts.entities.NotificationType;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.os.Build;

public class FriendListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_list, menu);
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private AbsListView mContactListList;
		private ArrayList<CommonItem> mDataSource;
		private CommonListAdapter mAdapter;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_common_list,
					container, false);
			init(rootView);
			return rootView;
		}

		private void init(View rootView) {
			mDataSource = new ArrayList<CommonItem>();
			mAdapter = new CommonListAdapter(
					(FragmentActivity) this.getActivity(), mDataSource);
			mContactListList = (ListView) rootView.findViewById(R.id.listView3);

			mContactListList.setAdapter(mAdapter);

			mDataSource
					.add(new CommonItem("Long Nguyen", Constants.MOCKPICTURE));
			mDataSource.add(new CommonItem("Thanh Bui", Constants.MOCKPICTURE));
			mDataSource
					.add(new CommonItem("Khoa Trinh", Constants.MOCKPICTURE));
			mDataSource.add(new CommonItem("Ha Nguyen", Constants.MOCKPICTURE));
			mDataSource.add(new CommonItem("Swapnil Udar",
					Constants.MOCKPICTURE));
			mAdapter.notifyDataSetChanged();

		}

	}
}
