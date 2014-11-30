package fi.aalto.gringotts;

import fi.aalto.gringotts.entities.User;
import fi.aalto.gringotts.entities.UserList;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmPaymentActivity extends CommonActivity {
	private float mAmount;
	private String mReason;
	private User mTarget;
	private EditText mCodeBox;
	private boolean mIsSending;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_payment);
		setTitle("Confirm");
		
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		mAmount = bundle.getFloat("moneyAmount");
		mReason = bundle.getString("reason");
		mTarget = (User) i.getSerializableExtra("target");
		
		mIsSending = false;
		
		mCodeBox = (EditText) findViewById(R.id.security_code_box);
		TextView textView = (TextView) findViewById(R.id.recipient_text);
		textView.setText("Recipient: " + mTarget.Name);
		
		textView = (TextView) findViewById(R.id.facebook_text);
		textView.setText("Facebook: " + mTarget.Name);
		
		textView = (TextView) findViewById(R.id.amount_text);
		textView.setText(getString(R.string.amount_payment_text) + mAmount);
		
		textView = (TextView) findViewById(R.id.message_text);
		textView.setText("Reason: " + mReason);
		
		Button confirmPaymentBtn = (Button) findViewById(R.id.confirm_payment_btn);
		confirmPaymentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				hideKeyboard();
				if (mIsSending) {
					return;
				}
				mIsSending = true;
				
				String code = mCodeBox.getText().toString();
				if (code.length() > 0) {
					GringottsApplication application = (GringottsApplication) getApplication();
					TransactionTask task = new TransactionTask(UserList.getInstance().currentUser.Id, mTarget.Id, mAmount, mReason, application);
					task.execute();
				}
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirm_payment, menu);
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
	
	public class TransactionTask extends AsyncTask<Integer, Integer, Integer> {
		private final String sSender;
		private final String sReceiver;
		private final float sAmount;
		private final String sRemark;
		private final GringottsApplication application;

		public TransactionTask(String sender, String receiver, float amount, String remark, GringottsApplication application) {
			super();
			this.sSender = sender;
			this.sReceiver = receiver;
			this.sAmount = amount;
			this.sRemark = remark;
			this.application = application;
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			application.transaction(sSender, sReceiver, sAmount, sRemark);
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("TAG", "Sending money successfully");
			int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(mActivity, "The transaction has been completed successfully", duration);
	    	toast.show();
			mActivity.finish();
			
		}
	}
}
