package fi.aalto.gringotts;

import fi.aalto.displayingbitmaps.util.ImageFetcher;
import fi.aalto.gringotts.GringottsApplication.TransactionTask;
import fi.aalto.gringotts.entities.User;
import fi.aalto.gringotts.entities.UserList;
import fi.aalto.gringotts.utils.RoundedImageView;
import fi.aalto.gringotts.utils.SlideButton;
import fi.aalto.gringotts.utils.SlideButtonListener;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;
import android.provider.Settings.Global;

public class PaymentActivity extends CommonActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		setTitle("New Payment");
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ImageFetcher mImageFetcher;
		private int THUMB_SIZE = 240;
		private RoundedImageView userImage;
		private RoundedImageView targetImage;
		private Button plusButton;
		private TextView targetName;
		private EditText ammountTxt;
		private EditText messageTxt;
		private TextView ammountLabel;
		private SlideButton paymentButton;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_payment,
					container, false);
			mImageFetcher = ImageFetcher.createImageFetcher(
					(FragmentActivity) this.getActivity(), THUMB_SIZE);
			initUi(rootView);
			return rootView;
		}

		@Override
		public void onDestroy() {
			mImageFetcher.clearCache();
			super.onDestroy();
		}

		private void startConfirmScreen() {
			float am = Float.valueOf(ammountTxt.getText().toString());
			if (am == 0) {
				return;
			}
			String remark = messageTxt.getText().toString();

			Intent i = new Intent(this.getActivity(),
					ConfirmPaymentActivity.class);
			i.putExtra("moneyAmount", am);
			i.putExtra("reason", remark);
			i.putExtra("target", targetUser);
			Log.d("main", targetUser.Name);
			startActivity(i);
		}

		private void initUi(View rootView) {
			userImage = (RoundedImageView) rootView
					.findViewById(R.id.userImage);
			targetImage = (RoundedImageView) rootView
					.findViewById(R.id.targetImage);
			plusButton = (Button) rootView.findViewById(R.id.plus_button);
			targetName = (TextView) rootView.findViewById(R.id.target_name);
			ammountTxt = (EditText) rootView.findViewById(R.id.ammountBox);
			ammountLabel = (TextView) rootView.findViewById(R.id.ammountLabel);
			messageTxt = (EditText) rootView
					.findViewById(R.id.message_edit_text);
			paymentButton = (SlideButton) rootView
					.findViewById(R.id.paymentButton);
			hideTarget();
			plusButton.setOnClickListener(addContact);
			mImageFetcher.loadImage(Constants.MOCKPICTURE, userImage);
			paymentButton.setSlideButtonListener(new SlideButtonListener() {
				@Override
				public void handleSlide() {
					startConfirmScreen();
				}
			});

			ammountTxt.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					String amount = ammountTxt.getText().toString();
					ammountLabel.setText(getString(R.string.euro_symbol) + amount);
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}
			});
		}

		OnClickListener addContact = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PlaceholderFragment.this.getActivity(),
						FriendListSelectionActivity.class);
				PlaceholderFragment.this.startActivityForResult(i, 0);
			}
		};

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			User target = (User) data.getSerializableExtra("data");
			setTarget(target);
		}

		private User targetUser;

		public void hideTarget() {
			plusButton.setVisibility(View.VISIBLE);
			targetImage.setVisibility(View.GONE);
			targetName.setVisibility(View.INVISIBLE);
			targetUser = null;
		}

		public void setTarget(User user) {
			plusButton.setVisibility(View.GONE);
			targetImage.setVisibility(View.VISIBLE);
			targetName.setVisibility(View.VISIBLE);
			mImageFetcher.loadImage(user.Image, targetImage);
			targetName.setText(user.Name);
			targetImage.setOnTouchListener(dragOut);
			targetUser = user;
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
					if (!rect.contains(v.getLeft() + (int) event.getX(),
							v.getTop() + (int) event.getY())) {
						hideTarget();
					}
				}
				return true;
			}
		};

	}
}
