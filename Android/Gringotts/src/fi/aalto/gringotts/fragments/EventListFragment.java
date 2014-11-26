package fi.aalto.gringotts.fragments;

import java.util.ArrayList;
import java.util.List;

import fi.aalto.gringotts.R;
import fi.aalto.gringotts.adapters.EventListAdapter;
import fi.aalto.gringotts.entities.Event;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
 
public class EventListFragment extends Fragment {
	public static final int TYPE_ALL = 0;
	public static final int TYPE_HOSTING = 1;
	
	private int mType;
	private ListView mEventListView = null;
	private EventListAdapter mEventAdapter = null;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
        // get the type of each fragment
        Bundle args = getArguments();
        mType = args.getInt("type", 0);
         
        mEventListView = (ListView) rootView.findViewById(R.id.event_list_view);
        
        List<Event> eventList = new ArrayList<Event>();
        eventList.add(new Event("Slush After party", "http://mobilium.com/wp-content/uploads/2014/05/slush-logo-300px.jpg"));
        eventList.add(new Event("ESN trip to Lapland", "http://icons.iconarchive.com/icons/danleech/simple/512/facebook-icon.png"));
        
        
        mEventAdapter = new EventListAdapter(this.getActivity(), eventList);
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
