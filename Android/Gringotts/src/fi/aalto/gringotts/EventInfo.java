package fi.aalto.gringotts;

import java.util.List;

import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.entities.Event;
import fi.aalto.gringotts.entities.Ticket;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Build;

public class EventInfo extends FragmentActivity {
	private Event mEvent = null;
	private ImageFetcher mImageFetcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("TAG", "Onc reate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_info);
		
		
		// create fake event
		mEvent = new Event("Slush After Party", "");
		mEvent.date = "10 November 2014";
		mEvent.location = "Otaniemi";
		mEvent.isHost = false;
		mEvent.sellingTicket = true;
		mEvent.ticketPrice = 5;
		mEvent.ownTicket = new Ticket();
		
		setTitle(mEvent.name);
		
		setGuestIcons(mEvent.guests);
		
		if (mEvent.sellingTicket) {
			Button ticketBtn = (Button) findViewById(R.id.sellBuyTicketBtn);
			ticketBtn.setVisibility(View.VISIBLE);
			if (mEvent.isHost) {
				ticketBtn.setText("Sell Ticket");
				ticketBtn.setOnClickListener(sellTicketListener);
			} else if (mEvent.ownTicket == null){
				ticketBtn.setText("Buy Ticket");
				ticketBtn.setOnClickListener(buyTicketListener);
			} else {
				ticketBtn.setVisibility(View.GONE);
			}
		}
		
		if (mEvent.ownTicket != null) {
			setTicketInfo(mEvent.ownTicket);
		}
	}
	
	private void setGuestIcons(List<String> guests) {
		ImageView icon1 = (ImageView) findViewById(R.id.event_friend_icon1);
		ImageView icon2 = (ImageView) findViewById(R.id.event_friend_icon2);
		ImageView icon3 = (ImageView) findViewById(R.id.event_friend_icon3);
		ImageView icon4 = (ImageView) findViewById(R.id.event_friend_icon4);
		ImageView icon5 = (ImageView) findViewById(R.id.event_friend_icon5);
		
		mImageFetcher = ImageFetcher.createImageFetcher(this, 150);
		mImageFetcher.loadImage("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfp1/t31.0-1/c356.632.640.640/s320x320/10575148_4703141874061_7606606189367922067_o.jpg", icon1);
		mImageFetcher.loadImage("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c53.0.320.320/p320x320/10441159_10204259142908132_7197878491554619980_n.jpg?oh=b185314c46b5301b4348bb9c07154759&oe=54D58C76&__gda__=1426869981_8412f6c9cd42517945d6e24e12fd888b", icon2);
		mImageFetcher.loadImage("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p320x320/1377545_10204445545052307_2214021456999887520_n.jpg?oh=2cc8b6ba532d67213e62d55063a214dd&oe=5512BF59&__gda__=1426977333_11965521459cd5ada637f94efec5bdb3", icon3);
		mImageFetcher.loadImage("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xap1/v/t1.0-1/c21.0.320.320/p320x320/10441194_10152919400440757_5293454896535218108_n.jpg?oh=e324c2362975e3a70cf2f654eaea4418&oe=550F9327&__gda__=1427708216_0f852946d063ac7c3289b95cb8838cb9", icon4);
		mImageFetcher.loadImage("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xap1/v/t1.0-1/c0.0.320.320/p320x320/1186174_608129005892534_397623288_n.jpg?oh=24f7a7f34ddfe18982ca8514f71566ca&oe=5507DEDB&__gda__=1427386126_812e31473f722e650d20c49358bfb72d", icon5);
	}
	
	private void setTicketInfo(Ticket ticket) {
		LinearLayout ticketLayout = (LinearLayout) findViewById(R.id.ticket_info);
		ticketLayout.setVisibility(View.VISIBLE);
	}
	
	View.OnClickListener sellTicketListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
	};
	
	View.OnClickListener buyTicketListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_info, menu);
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

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.drawable.pull_in_left, R.drawable.push_out_right);
	}
}
