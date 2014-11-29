package fi.aalto.gringotts.mbdata;

public enum PaymentStatus {
	OPEN(1, "open"), CLOSE(2, "close"), REJECTED(3, "rejected"), CANCELLED(4,
			"cancelled");

	private final int sId;
	private final String sDesc;

	private PaymentStatus(int id, String desc) {
		this.sId = id;
		this.sDesc = desc;
	}

	public int getId() {
		return this.sId;
	}

	public String getDesc() {
		return this.sDesc;
	}

	public static PaymentStatus getPaymentStatus(int id) {
		for (PaymentStatus status : values()) {
			if (status.sId == id) {
				return status;
			}
		}
		throw new IllegalArgumentException("Wrong payment status id");
	}
}