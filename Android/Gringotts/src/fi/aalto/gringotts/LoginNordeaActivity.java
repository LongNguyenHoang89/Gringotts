package fi.aalto.gringotts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
;public class LoginNordeaActivity extends Activity {
	
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_nordea);
		
		mActivity = this;
		
		Button loginNordeaBtn = (Button) findViewById(R.id.login_nordea_btn);
		loginNordeaBtn.setOnClickListener(loginNordeaListener);
		
		Button noNordeaBtn = (Button) findViewById(R.id.no_nordea_btn);
		noNordeaBtn.setOnClickListener(noNordeaListener);
	}
	
	View.OnClickListener loginNordeaListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(mActivity, MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra("loggedIn", true);
			startActivity(i);
			mActivity.finish();
		}
	};
	
	View.OnClickListener noNordeaListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(mActivity, OtherBankInfoActivity.class);
			startActivity(i);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_nordea, menu);
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
}
