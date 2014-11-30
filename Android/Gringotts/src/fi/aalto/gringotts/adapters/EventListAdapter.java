package fi.aalto.gringotts.adapters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.R;
import fi.aalto.gringotts.entities.*;

public class EventListAdapter extends ArrayAdapter<Event> {
	public static int RESOURCE_ID = R.layout.list_event_row;
	public static int THUMB_SIZE = 150;
	
	private List<Event> mEvents;
	private LayoutInflater mInflater;
	private ImageFetcher mImageFetcher;

	public EventListAdapter(FragmentActivity context, List<Event> _events) {
		super(context, RESOURCE_ID, _events);
		
		mEvents = _events;
		mInflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mImageFetcher = ImageFetcher.createImageFetcher(context, THUMB_SIZE);
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(RESOURCE_ID, parent, false);
		}
		TextView eventNameView = (TextView) convertView.findViewById(R.id.event_name);
		eventNameView.setText(mEvents.get(position).name);
		
		ImageView iconView = (ImageView) convertView.findViewById(R.id.event_icon);
		mImageFetcher.loadImage(mEvents.get(position).url, iconView);
		
		ImageView iconTypeView = (ImageView) convertView.findViewById(R.id.event_type_icon);
		if (mEvents.get(position).isHost) {
			iconTypeView.setBackgroundResource(R.drawable.host);
		} else if (mEvents.get(position).ownTicket == null){
			iconTypeView.setBackgroundResource(R.drawable.exclamation_circle);
		} else {
			iconTypeView.setBackgroundResource(R.drawable.check);
		}
		
		 
		return convertView;
	}
	
//	private Bitmap loadImage(String urlStr) {
//		try {
//			URL url = new URL(urlStr);
//			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//			return bmp;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	@Override
    public void clear() {
        super.clear();
        mImageFetcher.closeCache();
    }

}
