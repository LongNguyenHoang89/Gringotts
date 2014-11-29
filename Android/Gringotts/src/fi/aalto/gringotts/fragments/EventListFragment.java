package fi.aalto.gringotts.fragments;

import java.util.ArrayList;
import java.util.List;

import fi.aalto.gringotts.EventInfo;
import fi.aalto.gringotts.R;
import fi.aalto.gringotts.adapters.EventListAdapter;
import fi.aalto.gringotts.entities.Event;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
				getActivity().overridePendingTransition(R.drawable.pull_in_right,
						R.drawable.push_out_left);
			}
		});

		mEventList = new ArrayList<Event>(); 
		mEventList.add(new Event("Slush After party", "http://highway.slush.org/wp-content/themes/slush2014/assets/img/slush-logo-red-300px.png"));
		mEventList.add(new Event("Nordea Innovation Challenge", "https://fbexternal-a.akamaihd.net/safe_image.php?d=AQAiq8trWsVKH9GL&w=484&h=253&url=http%3A%2F%2Fnordeainnovationchallenge.com%2Fwp-content%2Fuploads%2F2014%2F10%2Fnic_fb.png&cfs=1"));
		if (mType == TYPE_ALL) {
			mEventList.add(new Event("IBM Future Insight Afternoon", "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/1507923_10152503479981045_6537254921336061611_n.png?oh=bf417715ef9ec2c75f5a8ac9909e8739&oe=55135BD6&__gda__=1426523745_523707cf9a0f2f81f67e4779565be37a"));
			mEventList.add(new Event("Dinner at Vapiano", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRz9Vr8YFFDzTq8MTyjY5WVtbu40znBJytNk_YcmmlbSgJ4-MSnfxsJPFg"));
		}

		mEventAdapter = new EventListAdapter(this.getActivity(), mEventList);
		mEventListView.setAdapter(mEventAdapter);

		return rootView;
	}

	@Override
	public void onDestroy() {
		if (mEventAdapter != null) {
			mEventAdapter.clear();
		}
		super.onDestroy();
	}
}
