package com.kwikmedical.demo.events;

public class AmbulanceStatusUpdatedEvent {
    private final Long ambulanceId;
    private final String status;

    public AmbulanceStatusUpdatedEvent(Long ambulanceId, String status) {
        this.ambulanceId = ambulanceId;
        this.status = status;
    }

    public Long getAmbulanceId() {
        return ambulanceId;
    }

    public String getStatus() {
        return status;
    }
}

