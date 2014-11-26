package fi.aalto.gringotts;

import java.util.ArrayList;
import java.util.Date;

import fi.aalto.gringotts.adapter.TransactionListAdapter;
import fi.aalto.gringotts.entity.Notification;
import fi.aalto.gringotts.entity.NotificationType;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

public class MainActivity extends Activity {

	private AbsListView mRecentTransactionList;
	private ArrayList<Notification> mDataSource;
	private TransactionListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUi();
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
		mRecentTransactionList = (ListView) findViewById(R.id.listView1);

		mRecentTransactionList.setAdapter(mAdapter);

		mDataSource.add(new Notification("You paid Thanh for meal", new Date(), -100, NotificationType.PAYMENT));
		mDataSource.add(new Notification("Thanh paid you for being awesome", new Date(), 200, NotificationType.PAYMENT));
		mDataSource.add(new Notification("valar morghulis", new Date(), -500, NotificationType.PAYMENT));
		mAdapter.notifyDataSetChanged();
	}
}
