package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapters.NotificationListAdapter;
import fi.aalto.gringotts.adapters.TransactionListAdapter;
import fi.aalto.gringotts.entities.Notification;
import fi.aalto.gringotts.entities.NotificationType;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.AbsListView;
import android.widget.ListView;

public class NotificationActivity extends FragmentActivity {

	private AbsListView mNotificationList;
	private ArrayList<Notification> mDataSource;
	private TransactionListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		initUi();
	}

	private void initUi() {
		mDataSource = new ArrayList<Notification>();
		mAdapter = new TransactionListAdapter(this, mDataSource);
		mNotificationList = (ListView) findViewById(R.id.listView2);

		mNotificationList.setAdapter(mAdapter);

		mDataSource.add(new Notification("You paid Thanh for meal", new Date(), -100, NotificationType.PAYMENT, mockURL));
		mDataSource.add(new Notification("Thanh paid you for being awesome", new Date(), 200, NotificationType.PAYMENT, mockURL));
		mDataSource.add(new Notification("valar morghulis", new Date(), -500, NotificationType.PAYMENT, mockURL));
		mAdapter.notifyDataSetChanged();
	}

	private String mockURL = "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/479790_10200321575896139_725967998_n.jpg?oh=954050a5aaabd07244a39d5dc12023d1&oe=54D381C1&__gda__=1427122166_d8d4941b3a0aae6f90a7af0833181a07";
}
