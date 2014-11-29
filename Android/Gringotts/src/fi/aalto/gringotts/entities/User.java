package fi.aalto.gringotts.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	public String Name;
	public String Image;

	public User(CommonItem item) {
		this.Name = item.Content;
		this.Image = item.Url;
	}

	protected User(Parcel in) {
		Name = in.readString();
		Image = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Name);
		dest.writeString(Image);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
