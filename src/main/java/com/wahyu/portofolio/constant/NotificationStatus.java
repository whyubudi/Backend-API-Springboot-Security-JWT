package com.wahyu.portofolio.constant;

public enum NotificationStatus {

    SUCCESS(0),
    FAILED(1);

    private final int status;

    NotificationStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
    
    public static NotificationStatus getValue(int status) {
		for (NotificationStatus e : values()) {
			if (e.getStatus() == status) {
				return e;
			}
		}
		
		return null;
	}
}
