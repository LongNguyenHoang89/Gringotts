package fi.aalto.gringotts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

import fi.aalto.gringotts.adapters.CommonListAdapter;
import fi.aalto.gringotts.entities.CommonItem;
import fi.aalto.gringotts.entities.NotificationType;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends CommonActivity {

	private AbsListView mRecentTransactionList;
	private ArrayList<CommonItem> mDataSource;
	private CommonListAdapter mAdapter;
	private Button notificationButton;
	private Button friendButton;
	private Button EventButton;
	private Button PayButton;
	private Button ChargeButton;
	private Button SettingButton;
	
	// start defining variables for Push
	private IBMPush push = null;
	private IBMPushNotificationListener notificationListener = null;
	
	private static final String CLASS_NAME = MainActivity.class.getSimpleName();
	private static final String APP_ID = "applicationID";
    private static final String APP_SECRET = "applicationSecret";
    private static final String APP_ROUTE = "applicationRoute";
    private static final String PROPS_FILE = "bluelist.properties";
    // end defining variables for Push

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUi();
		setTitle("Popcoin");
		hideActionBarIcon();
		fakeData();
		
		// init push services
		initPushService();
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        registerPushListener();
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
		EventButton = (Button) findViewById(R.id.imageButton1);
		notificationButton = (Button) findViewById(R.id.imageButton2);
		friendButton = (Button) findViewById(R.id.imageButton3);
		PayButton = (Button) findViewById(R.id.imageButton4);
		ChargeButton = (Button) findViewById(R.id.imageButton5);
		SettingButton = (Button) findViewById(R.id.imageButton6);

		mRecentTransactionList.setAdapter(mAdapter);

		EventButton.setOnClickListener(buttonClick);
		notificationButton.setOnClickListener(buttonClick);
		PayButton.setOnClickListener(buttonClick);
		ChargeButton.setOnClickListener(buttonClick);
		SettingButton.setOnClickListener(buttonClick);
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
			case R.id.imageButton1:
				ii = new Intent(MainActivity.this, EventList.class);
				break;
			case R.id.imageButton2:
				ii = new Intent(MainActivity.this, NotificationActivity.class);
				break;
			case R.id.imageButton3:
				ii = new Intent(MainActivity.this, FriendListActivity.class);
				break;
			case R.id.imageButton4:
				ii = new Intent(MainActivity.this, PaymentActivity.class);
				break;
			case R.id.imageButton5:
				ii = new Intent(MainActivity.this, ChargeActivity.class);
				break;
			}

			if (ii != null) {
				startActivity(ii);

				// http://madcoda.com/2013/09/android-activity-transition-slide-in-out-animation/
				overridePendingTransition(R.drawable.pull_in_right, R.drawable.push_out_left);
			}
		}
	};

	private void fakeData() {
		mDataSource.add(new CommonItem("You paid Thanh for meal", new Date(), -100, NotificationType.PAYMENT));
		mDataSource.add(new CommonItem("Thanh paid you for being awesome", new Date(), 200, NotificationType.PAYMENT));
		mDataSource.add(new CommonItem("valar morghulis", new Date(), -500, NotificationType.PAYMENT));
		mAdapter.notifyDataSetChanged();
	}
	
	private void initPushService() {
		// Read from properties file.
        Properties props = new java.util.Properties();
        Context context = getApplicationContext();
        try {
            AssetManager assetManager = context.getAssets();
            props.load(assetManager.open(PROPS_FILE));
            Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
        } catch (FileNotFoundException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
        } catch (IOException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
        }
        Log.i(CLASS_NAME, "Application ID is: " + props.getProperty(APP_ID));

        // Initialize the IBM core backend-as-a-service.
        IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
		
		push = IBMPush.initializeService();
		push.register("DemoDeviceFinal", "DemoApp").continueWith(new Continuation<String, Void>() {

            @Override
            public Void then(Task<String> task) throws Exception {

                if (task.isFaulted()) {
                    Log.i(CLASS_NAME,"Error registering with Push Service. " + task.getError().getMessage() + "\n"
                            + "Push notifications will not be received.");
                } else {
                	String deviceId= task.getResult();
                	// TODO: client should send deviceId to server		
                			
                    Log.i(CLASS_NAME,"Device is registered with Push Service" + "\n" + "Device Id : " + deviceId);
                }
                return null;
            }
        });
		
		notificationListener = new IBMPushNotificationListener() {

			@Override
			public void onReceive(final IBMSimplePushNotification message) {
				fakeData();	
				Log.i(CLASS_NAME, message.toString());
			}

		};
		
	}
	
	private void registerPushListener() {
		if (push != null) {
            push.listen(notificationListener);
        }
	}
	
}
