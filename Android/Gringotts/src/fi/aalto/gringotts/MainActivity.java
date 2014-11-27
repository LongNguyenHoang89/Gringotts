package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapters.CommonListAdapter;
import fi.aalto.gringotts.entities.CommonItem;
import fi.aalto.gringotts.entities.NotificationType;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private AbsListView mRecentTransactionList;
	private ArrayList<CommonItem> mDataSource;
	private CommonListAdapter mAdapter;
	private Button notificationButton;
	private Button friendButton;

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
		mDataSource = new ArrayList<CommonItem>();
		mAdapter = new CommonListAdapter(this, mDataSource);

		// Populate UI object from xml
		mRecentTransactionList = (ListView) findViewById(R.id.listView1);
		notificationButton = (Button) findViewById(R.id.imageButton2);
		friendButton = (Button) findViewById(R.id.imageButton3);

		mRecentTransactionList.setAdapter(mAdapter);

		notificationButton.setOnClickListener(buttonClick);
		friendButton.setOnClickListener(buttonClick);
	}

	/**
	 * What happen when we click a button
	 */
	OnClickListener buttonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent ii = null;
			switch (v.getId()) {
			case R.id.imageButton2:
				ii = new Intent(MainActivity.this, NotificationActivity.class);
				break;
			case R.id.imageButton3:
				ii = new Intent(MainActivity.this, FriendListActivity.class);
				break;
			}

			if (ii != null) {
				startActivity(ii);
			}
		}
	};

	private void fakeData() {
		mDataSource.add(new CommonItem("You paid Thanh for meal", new Date(),
				-100, NotificationType.PAYMENT));
		mDataSource.add(new CommonItem("Thanh paid you for being awesome",
				new Date(), 200, NotificationType.PAYMENT));
		mDataSource.add(new CommonItem("valar morghulis", new Date(), -500,
				NotificationType.PAYMENT));
		mAdapter.notifyDataSetChanged();
	}
}
