package fi.aalto.gringotts.mbdata;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * 
 * This class represents DB table charge. This table maps amount to be paid for
 * an event with individual recipient facebook id.
 * 
 */
@IBMDataObjectSpecialization("Charge")
public class Charge extends IBMDataObject {
	private static final String TAG = Charge.class.getSimpleName();

	// DB columns
	private static final String EVENT_ID = "EVENT_ID";
	private static final String PAID_BY_FB_ID = "PAID_BY_FB_ID";
	private static final String AMOUNT = "AMOUNT";
	private static final String STATUS = "STATUS";

	public String getEventId() {
		return (String) getObject(EVENT_ID);
	}

	public String getPaidByFBId() {
		return (String) getObject(PAID_BY_FB_ID);
	}

	public float getAmount() {
		return (Float) getObject(AMOUNT);
	}

	public PaymentStatus getStatus() {
		return PaymentStatus.getPaymentStatus((Integer) getObject(STATUS));
	}

	public void setEventId(String eventId) {
		setObject(EVENT_ID, (eventId != null) ? eventId : "");
	}

	public void setPaidByFbId(String paidByFBId) {
		setObject(PAID_BY_FB_ID, (paidByFBId != null) ? paidByFBId : "");
	}

	public void setAmount(float amount) {
		setObject(AMOUNT, amount);
	}

	public void setStatus(PaymentStatus paymentStatus) {
		setObject(STATUS, paymentStatus.getId());
	}

	@Override
	public String toString() {
		return "Charge : " + getEventId() + " | " + getPaidByFBId() + " | "
				+ getAmount() + " | " + getStatus().getDesc();
	}
}