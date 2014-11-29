package fi.aalto.gringotts;

import java.text.DecimalFormat;

import fi.aalto.gringotts.entities.Event;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View.OnClickListener;

public class TicketSaleAcitivity extends CommonActivity {
	private Event mEvent;
	private EditText mTicketPriceView;
	private EditText mTicketNumberView;
	private TextView mRevenue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_sale);
		
		Intent intent = getIntent();
		
		mEvent = (Event) intent.getSerializableExtra("eventInfo");
		
		
		// Add text change listener to calculate revenue
		mTicketPriceView = (EditText) findViewById(R.id.ticket_price_box);
		mTicketPriceView.addTextChangedListener(revenueWatcher);
		mTicketNumberView = (EditText) findViewById(R.id.ticket_amount_box);
		mTicketNumberView.addTextChangedListener(revenueWatcher);
		
		// revenue view
		mRevenue = (TextView) findViewById(R.id.total_revenue);
		setRevenue(0);
		
		// set Sell Ticket listener
		Button sellTicketBtn = (Button) findViewById(R.id.sellTicketBtn);
		sellTicketBtn.setOnClickListener(sellTicketListener);
		
		// set title
		setTitle(mEvent.name);
	}
	
	View.OnClickListener sellTicketListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
		}
	};
	
	private void setRevenue(double val) {
		DecimalFormat df = new DecimalFormat("####0.00");
		mRevenue.setText(getString(R.string.total_revenue_text) + df.format(val));
	}
	
	TextWatcher revenueWatcher = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable arg0) {
			String ticketPrice = mTicketPriceView.getText().toString();
			String ticketNumber = mTicketNumberView.getText().toString();
			double revenue = 0;
			if (ticketPrice.length() > 0 && ticketNumber.length() > 0) {
 				revenue = Double.parseDouble(ticketPrice) * Integer.parseInt(ticketNumber);
			}
			setRevenue(revenue);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1,
				int arg2, int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ticket_sale_acitivity, menu);
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
