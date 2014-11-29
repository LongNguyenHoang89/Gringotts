package fi.aalto.gringotts.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.FriendListSelectionActivity;
import fi.aalto.gringotts.R;
import fi.aalto.gringotts.entities.Event;
import fi.aalto.gringotts.entities.User;
import fi.aalto.gringotts.utils.RoundedImageView;
import fi.aalto.gringotts.utils.RowLayout;

public class GroupChargeFragment extends Fragment {
	public static final int COLS_NUM = 5;
	public static final int THUMB_SIZE = 150;
	public static final int SPACING = 10;

	private Event mEvent = null;
	private List<User> mUserList = null;
	private ImageFetcher mImageFetcher = null;
	private RowLayout mViewParent;

	public GroupChargeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_group_charge, container,
				false);
		Bundle args = this.getArguments();
		if (args != null) {
			mEvent = (Event) args.getSerializable("eventInfo");
		}
		
		mViewParent = (RowLayout) rootView
				.findViewById(R.id.group_charge_users);

		// initialize image fetcher
		mImageFetcher = ImageFetcher.createImageFetcher(
				(FragmentActivity) getActivity(), THUMB_SIZE);

		if (mEvent != null) {
			createFakeData();
		} else {
			mUserList = new ArrayList<User>();
		}

		refreshUserList(mUserList);
		return rootView;
	}

	@Override
	public void onDestroy() {
		mImageFetcher.clearCache();
		super.onDestroy();
	}

	private void refreshUserList(List<User> userList) {
		mViewParent.removeAllViews();

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);
		int size = screenSize.x / COLS_NUM - 22;
		LayoutParams params = new LayoutParams(size, size);

		if (userList != null) {
			for (User user : userList) {
			RoundedImageView newView = new RoundedImageView(getActivity());
			// newView.setId(user.Id);
			newView.setLayoutParams(params);
			newView.setAdjustViewBounds(true);
			newView.setScaleType(ImageView.ScaleType.FIT_CENTER);

			mImageFetcher.loadImage(user.Image, newView);
			newView.setOnTouchListener(dragOut);
			mViewParent.addView(newView);
		}
		}

		Drawable plusImg = getResources().getDrawable(R.drawable.plus_circle);
		Button plusBtn = new Button(getActivity());
		plusBtn.setLayoutParams(params);
		plusBtn.setBackgroundDrawable(plusImg);
		mViewParent.addView(plusBtn);

		plusBtn.setOnClickListener(addUserListener);
	}

	View.OnClickListener addUserListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(getActivity(),
					FriendListSelectionActivity.class);
			startActivityForResult(i, 0);
		}

	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		User newUser = (User) data.getSerializableExtra("data");
		mUserList.add(newUser);
		refreshUserList(mUserList);
	}

	OnTouchListener dragOut = new OnTouchListener() {
		private Rect rect; // Variable rect to hold the bounds of the view

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// Construct a rect of the view's bounds
				rect = new Rect(v.getLeft(), v.getTop(), v.getRight(),
						v.getBottom());
			}

			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop()
						+ (int) event.getY())) {
					mViewParent.removeView(v);
				}
			}
			return true;
		}
	};

	private void createFakeData() {
		mUserList = new ArrayList<User>();

		mUserList
				.add(new User(
						"11111111",
						"",
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfp1/t31.0-1/c356.632.640.640/s320x320/10575148_4703141874061_7606606189367922067_o.jpg"));
		mUserList
				.add(new User(
						"22222222",
						"",
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c53.0.320.320/p320x320/10441159_10204259142908132_7197878491554619980_n.jpg?oh=b185314c46b5301b4348bb9c07154759&oe=54D58C76&__gda__=1426869981_8412f6c9cd42517945d6e24e12fd888b"));
		mUserList
				.add(new User(
						"33333333",
						"",
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p320x320/1377545_10204445545052307_2214021456999887520_n.jpg?oh=2cc8b6ba532d67213e62d55063a214dd&oe=5512BF59&__gda__=1426977333_11965521459cd5ada637f94efec5bdb3"));
		mUserList
				.add(new User(
						"44444444",
						"",
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xap1/v/t1.0-1/c21.0.320.320/p320x320/10441194_10152919400440757_5293454896535218108_n.jpg?oh=e324c2362975e3a70cf2f654eaea4418&oe=550F9327&__gda__=1427708216_0f852946d063ac7c3289b95cb8838cb9"));
		mUserList
				.add(new User(
						"55555555",
						"",
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xap1/v/t1.0-1/c0.0.320.320/p320x320/1186174_608129005892534_397623288_n.jpg?oh=24f7a7f34ddfe18982ca8514f71566ca&oe=5507DEDB&__gda__=1427386126_812e31473f722e650d20c49358bfb72d"));

	}

}
