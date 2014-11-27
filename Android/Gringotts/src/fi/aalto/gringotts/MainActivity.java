package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapters.TransactionListAdapter;
import fi.aalto.gringotts.entities.Notification;
import fi.aalto.gringotts.entities.NotificationType;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	private AbsListView mRecentTransactionList;
	private ArrayList<Notification> mDataSource;
	private TransactionListAdapter mAdapter;
	private Button notificationButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUi();
		fakeData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	private void initUi() {
		mDataSource = new ArrayList<Notification>();
		mAdapter = new TransactionListAdapter(this, mDataSource);

		// Populate UI object from xml
		mRecentTransactionList = (ListView) findViewById(R.id.listView1);
		notificationButton = (Button) findViewById(R.id.imageButton2);

		mRecentTransactionList.setAdapter(mAdapter);
		notificationButton.setOnClickListener(notificationClick);
	}

	/**
	 * What happen when we click a button
	 */
	OnClickListener notificationClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent ii = new Intent(MainActivity.this, NotificationActivity.class);
			startActivity(ii);
		}
	};

	private void fakeData() {
		mDataSource.add(new Notification("You paid Thanh for meal", new Date(), -100, NotificationType.PAYMENT));
		mDataSource.add(new Notification("Thanh paid you for being awesome", new Date(), 200, NotificationType.PAYMENT));
		mDataSource.add(new Notification("valar morghulis", new Date(), -500, NotificationType.PAYMENT));
		mAdapter.notifyDataSetChanged();
	}
}
