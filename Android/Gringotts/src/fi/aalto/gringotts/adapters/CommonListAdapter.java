package fi.aalto.gringotts.adapters;

import java.text.DateFormat;
import java.util.ArrayList;

import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.MainActivity;
import fi.aalto.gringotts.PaymentActivity;
import fi.aalto.gringotts.R;
import fi.aalto.gringotts.entities.CommonItem;
import fi.aalto.gringotts.entities.NotificationType;
import fi.aalto.gringotts.utils.RoundedImageView;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonListAdapter extends ArrayAdapter<CommonItem> {
	private Context mContext;
	private ArrayList<CommonItem> mDataSource;
	private ImageFetcher mImageFetcher;
	public static int THUMB_SIZE = 167;

	public CommonListAdapter(FragmentActivity context, ArrayList<CommonItem> data) {
		super(context, R.layout.item_browserlist, data);

		this.mContext = context;
		this.mDataSource = data;

		mImageFetcher = ImageFetcher.createImageFetcher(context, THUMB_SIZE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;

		CommonItem note = this.getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_browserlist, parent, false);
			mViewHolder = new ViewHolder(convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.setTag(note);
		mViewHolder.topView.setText(note.Content);

		if (note.Time != null) {
			DateFormat formatDate = DateFormat.getDateTimeInstance();
			mViewHolder.dateview.setText(formatDate.format(note.Time));
		} else {
			mViewHolder.dateview.setVisibility(View.GONE);
		}

		if (!note.Url.isEmpty()) {
			mViewHolder.icon.setVisibility(View.VISIBLE);
			mImageFetcher.loadImage(note.Url, mViewHolder.icon);
		} else {
			mViewHolder.icon.setVisibility(View.GONE);
		}

		if (note.Ammount > 0) {
			mViewHolder.bottomView.setTextColor(Color.GREEN);
		} else if (note.Ammount < 0) {
			mViewHolder.bottomView.setTextColor(Color.RED);
		} else {
			mViewHolder.bottomView.setVisibility(View.GONE);
		}

		if (note.Type == NotificationType.CHARGE) {
			mViewHolder.payButton.setVisibility(View.VISIBLE);
			mViewHolder.payButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent ii = new Intent(mContext, PaymentActivity.class);
					mContext.startActivity(ii);
				}
			});

			mViewHolder.bottomView.setVisibility(View.GONE);
		}

		mViewHolder.bottomView.setText(Double.toString(note.Ammount));

		return convertView;
	}

	@Override
	public CommonItem getItem(int pos) {
		return mDataSource.get(pos);
	}

	public static class ViewHolder {
		public TextView topView;
		public TextView bottomView;
		public TextView dateview;
		public RoundedImageView icon;
		public Button payButton;
		public Object tag;

		ViewHolder(View view) {
			topView = (TextView) view.findViewById(R.id.top_view);
			bottomView = (TextView) view.findViewById(R.id.bottom_view);
			dateview = (TextView) view.findViewById(R.id.dateview);
			icon = (RoundedImageView) view.findViewById(R.id.row_image);
			payButton = (Button) view.findViewById(R.id.paymentButton);
		}

		public Object getTag() {
			return tag;
		}

		public void setTag(Object t) {
			this.tag = t;
		}
	}
}
