package fi.aalto.gringotts.mbdata;

import java.util.Date;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * 
 * This class represents DB table account.
 * 
 */
@IBMDataObjectSpecialization("Account")
public class Account extends IBMDataObject {
	private static final String TAG = Account.class.getSimpleName();

	// DB columns
	private static final String FB_ID = "FB_ID";
	private static final String IBAN = "IBAN";
	private static final String NAME = "NAME";
	private static final String ADDR = "ADDR";
	private static final String REMARK = "REMARK";
	private static final String TSMP = "TSMP";

	public String getFacebookId() {
		return (String) getObject(FB_ID);
	}

	public String getIBAN() {
		return (String) getObject(IBAN);
	}

	public String getName() {
		return (String) getObject(NAME);
	}

	public String getAddress() {
		return (String) getObject(ADDR);
	}

	public String getRemark() {
		return (String) getObject(REMARK);
	}

	public long getTimestamp() {
		return (Long) getObject(TSMP);
	}

	public void setFacebookId(String facebookId) {
		setObject(FB_ID, (facebookId != null) ? facebookId : "");
	}

	public void setIBAN(String iban) {
		setObject(IBAN, (iban != null) ? iban : "");
	}

	public void setName(String name) {
		setObject(NAME, (name != null) ? name : "");
	}

	public void setAddress(String address) {
		setObject(ADDR, (address != null) ? address : "");
	}

	public void setRemark(String remark) {
		setObject(REMARK, (remark != null) ? remark : "");
	}

	public void setTimestamp() {
		setObject(TSMP, System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return "Account : " + new Date(getTimestamp()) + " | " + getFacebookId() + " | "
				+ getIBAN() + " | " + getName() + " | " + getAddress() + " | "
				+ getRemark();
	}
}