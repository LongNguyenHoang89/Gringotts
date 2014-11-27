package fi.aalto.gringotts.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.entities.Notification;

/**
 * Provide extra image in the top of the notification item
 * 
 * @author eledra
 */
public class NotificationListAdapter extends TransactionListAdapter {

	public static int THUMB_SIZE = 90;
	private ImageFetcher mImageFetcher;

	public NotificationListAdapter(FragmentActivity context, ArrayList<Notification> data) {
		super(context, data);
		mImageFetcher = ImageFetcher.createImageFetcher(context, THUMB_SIZE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Notification note = this.getItem(position);
		ViewHolder myView = (ViewHolder) super.getView(position, convertView, parent).getTag();

		myView.icon.setVisibility(View.VISIBLE);
		//mImageFetcher.loadImage(note.Url, myView.icon);

		return convertView;
	}
}
