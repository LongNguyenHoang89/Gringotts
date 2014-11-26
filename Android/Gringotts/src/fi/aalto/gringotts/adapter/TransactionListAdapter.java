package fi.aalto.gringotts.adapter;

import java.util.ArrayList;

import fi.aalto.gringotts.R;
import fi.aalto.gringotts.entity.Notification;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionListAdapter extends ArrayAdapter<Notification> {
	private Context mContext;
	private Resources mResources;
	private ArrayList<Notification> mDataSource;

	public TransactionListAdapter(final Context context, ArrayList<Notification> data) {
		super(context, R.layout.item_browserlist, data);

		this.mContext = context;
		this.mDataSource = data;
		this.mResources = context.getResources();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		int num_items = 0;

		Notification note = this.getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_browserlist, parent, false);
			mViewHolder = new ViewHolder(convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.topView.setText(note.Content);
		mViewHolder.dateview.setText(note.Time.toString());
		mViewHolder.icon.setVisibility(View.GONE);

		if (note.Ammount > 0) {
			mViewHolder.bottomView.setTextColor(Color.GREEN);
		} else {
			mViewHolder.bottomView.setTextColor(Color.RED);
		}
		mViewHolder.bottomView.setText(Integer.toString(note.Ammount));

		return convertView;
	}

	@Override
	public Notification getItem(int pos) {
		return mDataSource.get(pos);
	}

	private static class ViewHolder {
		TextView topView;
		TextView bottomView;
		TextView dateview;
		ImageView icon;

		ViewHolder(View view) {
			topView = (TextView) view.findViewById(R.id.top_view);
			bottomView = (TextView) view.findViewById(R.id.bottom_view);
			dateview = (TextView) view.findViewById(R.id.dateview);
			icon = (ImageView) view.findViewById(R.id.row_image);
		}
	}
}