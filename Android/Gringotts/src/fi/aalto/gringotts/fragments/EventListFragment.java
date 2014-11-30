package fi.aalto.gringotts.fragments;

import java.util.ArrayList;
import java.util.List;

import fi.aalto.gringotts.EventInfo;
import fi.aalto.gringotts.R;
import fi.aalto.gringotts.adapters.EventListAdapter;
import fi.aalto.gringotts.entities.Event;
import fi.aalto.gringotts.entities.Ticket;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EventListFragment extends Fragment {
	public static final int TYPE_ALL = 0;
	public static final int TYPE_HOSTING = 1;

	private int mType;
	private ListView mEventListView = null;
	private EventListAdapter mEventAdapter = null;
	private List<Event> mEventList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
		// get the type of each fragment
		Bundle args = getArguments();
		mType = args.getInt("type", 0);

		mEventListView = (ListView) rootView.findViewById(R.id.event_list_view);
		mEventListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Intent i = new Intent(getActivity(), EventInfo.class);
				i.putExtra("eventInfo", mEventList.get(pos));
				startActivity(i);
//				getActivity().overridePendingTransition(R.drawable.pull_in_right,
//						R.drawable.push_out_left);
			}
		});

		fakeData();

		mEventAdapter = new EventListAdapter((FragmentActivity) this.getActivity(), mEventList);
		mEventListView.setAdapter(mEventAdapter);

		return rootView;
	}
	
	private void fakeData() {
		mEventList = new ArrayList<Event>(); 
		Event event = new Event("Weekend dinner", "http://cherokee.agrilife.org/files/2012/07/DinnerTonight.jpg");
		event.date = "10 December 2014";
		event.location = "Otaniemi";
		event.isHost = true;
		event.sellingTicket = false;
		mEventList.add(event);
		
		event = new Event("BEST Cantus Party", "http://besthelsinki.fi/wp-content/uploads/2014/11/cantus-660x295.jpg");
		event.date = "21 December 2014";
		event.location = "Helsinki";
		event.isHost = true;
		event.sellingTicket = true;
		event.ticketPrice = 5;
		event.soldTicket = 130;
		event.totalTicket = 200;
		mEventList.add(event);
		
		if (mType == TYPE_ALL) {
			event = new Event("IBM Future Insight", "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/1507923_10152503479981045_6537254921336061611_n.png?oh=bf417715ef9ec2c75f5a8ac9909e8739&oe=55135BD6&__gda__=1426523745_523707cf9a0f2f81f67e4779565be37a");
			event.date = "25 November 2014";
			event.location = "Helsinki";
			event.isHost = false;
			event.ownTicket = null;
			mEventList.add(event);
		
			event = new Event("Nordea Challenge", "https://fbexternal-a.akamaihd.net/safe_image.php?d=AQAiq8trWsVKH9GL&w=484&h=253&url=http%3A%2F%2Fnordeainnovationchallenge.com%2Fwp-content%2Fuploads%2F2014%2F10%2Fnic_fb.png&cfs=1");
			event.date = "29 November 2014";
			event.location = "Startup Sauna";
			event.isHost = false;
			event.ownTicket = new Ticket("1231323");
			mEventList.add(event);
		}
	}

	@Override
	public void onDestroy() {
		if (mEventAdapter != null) {
			mEventAdapter.clear();
		}
		super.onDestroy();
	}
}
