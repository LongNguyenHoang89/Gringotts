package fi.aalto.gringotts.mbdata;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Transaction details.
 */
@IBMDataObjectSpecialization("Transaction")
public class Transaction extends IBMDataObject {
	private static final String TAG = Transaction.class.getSimpleName();

	// DB column
	private static final String SENDER_FB_ID = "SENDER_FB_ID";
	private static final String RECEIVER_FB_ID = "RECEIVER_FB_ID";
	private static final String AMOUNT = "AMOUNT";
	private static final String REMARK = "REMARK";

	public String getSenderFBId() {
		return (String) getObject(SENDER_FB_ID);
	}

	public String getReceiverFBId() {
		return (String) getObject(RECEIVER_FB_ID);
	}

	public float getAmount() {
		return (Float) getObject(AMOUNT);
	}

	public String getRemark() {
		return (String) getObject(REMARK);
	}

	public void setSenderFBId(String facebookId) {
		setObject(SENDER_FB_ID, (facebookId != null) ? facebookId : "");
	}

	public void setReceiverFBId(String facebookId) {
		setObject(RECEIVER_FB_ID, (facebookId != null) ? facebookId : "");
	}

	public void setAmount(float amount) {
		setObject(AMOUNT, amount);
	}

	public void setRemark(String remark) {
		setObject(REMARK, (remark != null) ? remark : "");
	}

	@Override
	public String toString() {
		return "Transaction : " + getSenderFBId() + " | " + getReceiverFBId()
				+ " | " + getAmount() + " | " + getRemark();
	}
}