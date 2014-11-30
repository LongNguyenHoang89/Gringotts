package fi.aalto.gringotts.entities;

import java.io.Serializable;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification implements Serializable {
	public NotificationType Type;
	public String Content;
	public User Issuer;
	public User Target;
	public double amount;
	public Date DateTime;
	public boolean IsNew;
	public String Id;
	
	public CommonItem ToCommonItem() {
		return new CommonItem(Content, DateTime, amount, Type, Issuer.Image);
	}
}
