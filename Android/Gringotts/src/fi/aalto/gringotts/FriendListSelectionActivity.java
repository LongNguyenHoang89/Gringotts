package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapters.CommonListAdapter;
import fi.aalto.gringotts.adapters.CommonListAdapter.ViewHolder;
import fi.aalto.gringotts.entities.CommonItem;
import fi.aalto.gringotts.entities.NotificationType;
import fi.aalto.gringotts.entities.User;
import fi.aalto.gringotts.entities.UserList;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class FriendListSelectionActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Contacts");
		setContentView(R.layout.activity_friend_list);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private AbsListView mContactList;
		private ArrayList<CommonItem> mDataSource;
		private CommonListAdapter mAdapter;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_common_list, container, false);
			init(rootView);
			return rootView;
		}

		private void init(View rootView) {
			mDataSource = new ArrayList<CommonItem>();
			mAdapter = new CommonListAdapter((FragmentActivity) this.getActivity(), mDataSource);
			mContactList = (ListView) rootView.findViewById(R.id.listView3);
			mContactList.setAdapter(mAdapter);
			mContactList.setOnItemClickListener(itemClick);

			for (int i = 1; i <= UserList.getInstance().mockUser.size(); i++) {
				mDataSource.add(new CommonItem(UserList.getInstance().getUser(String.valueOf(i))));
			}

			mAdapter.notifyDataSetChanged();
		}

		OnItemClickListener itemClick = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent resultIntent = new Intent();
				ViewHolder t = (ViewHolder) arg1.getTag();
				CommonItem item = (CommonItem) t.getTag();
				User selected = new User(item);
				resultIntent.putExtra("data", selected);
				PlaceholderFragment.this.getActivity().setResult(0, resultIntent);
				PlaceholderFragment.this.getActivity().finish();
			}
		};
	}
}
