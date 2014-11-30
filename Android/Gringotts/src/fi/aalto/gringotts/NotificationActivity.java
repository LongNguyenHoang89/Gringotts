package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapters.CommonListAdapter;
import fi.aalto.gringotts.entities.CommonItem;
import fi.aalto.gringotts.entities.Notification;
import fi.aalto.gringotts.entities.NotificationType;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.AbsListView;
import android.widget.ListView;

public class NotificationActivity extends CommonActivity {

	private AbsListView mNotificationList;
	private ArrayList<CommonItem> mDataSource;
	private CommonListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Notifications");
		setContentView(R.layout.activity_notification);
		initUi();
	}

	private void initUi() {
		mDataSource = new ArrayList<CommonItem>();
		mAdapter = new CommonListAdapter(this, mDataSource);
		mNotificationList = (ListView) findViewById(R.id.listView2);
		mNotificationList.setAdapter(mAdapter);

		ArrayList<Notification> notification = NotificationManager.getInstance().getNotificationList();
		for (int i = notification.size() - 1; i >= 0; i--) {
			mDataSource.add(notification.get(i).ToCommonItem());
		}
		mAdapter.notifyDataSetChanged();
		// fakeData();
	}

	private void fakeData() {
		mDataSource.add(new CommonItem("You paid Thanh for meal", new Date(), -100, NotificationType.PAYMENT, Constants.MOCKPICTURE));
		mDataSource.add(new CommonItem("Thanh paid you for being awesome", new Date(), 200, NotificationType.PAYMENT, Constants.MOCKPICTURE));
		mDataSource.add(new CommonItem("valar morghulis", new Date(), -500, NotificationType.PAYMENT, Constants.MOCKPICTURE));
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.drawable.pull_in_left, R.drawable.push_out_right);
	}
}
