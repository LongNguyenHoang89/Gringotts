package fi.aalto.gringotts.entities;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Serializable {
	public String Id;
	public String Name;
	public String Image;

	public User(CommonItem item) {
		this.Name = item.Content;
		this.Image = item.Url;
		this.Id = item.Id;
	}
	
	public User(String _id, String _name, String _url) {
		this.Name = _name;
		this.Image = _url;
		this.Id = _id;
	}
	
}
