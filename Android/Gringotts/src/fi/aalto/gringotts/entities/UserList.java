package fi.aalto.gringotts.entities;

import java.io.Serializable;
import java.util.ArrayList;

import fi.aalto.gringotts.Constants;
import android.os.Parcel;
import android.os.Parcelable;

public class UserList {
	private static UserList INSTANCE;

	public static UserList getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserList();
		}
		return INSTANCE;
	}

	public ArrayList<User> mockUser;

	public UserList() {
		mockUser = new ArrayList<User>();
		mockUser.add(new User("1", "Long Nguyen", Constants.MOCKPICTURE));
		mockUser.add(new User("2", "Thanh Bui", Constants.MOCKPICTURE1));
		mockUser.add(new User("3", "Khoa Trinh", Constants.MOCKPICTURE3));
		mockUser.add(new User("4", "Ha Nguyen", Constants.MOCKPICTURE4));
		mockUser.add(new User("5", "Swapnil Udar", Constants.MOCKPICTURE2));
	}

	/**
	 * get mock user base on their id
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		for (int i = 0; i < mockUser.size(); i++) {
			User t = mockUser.get(i);
			if (t.Id.equals(id)) {
				return t;
			}
		}
		return null;
	}
}
