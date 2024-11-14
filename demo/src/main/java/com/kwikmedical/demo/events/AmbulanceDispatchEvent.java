package com.kwikmedical.demo.events;

public class AmbulanceDispatchEvent {
    private Long ambulanceId;
    private String patientId;
    private String location;

    public AmbulanceDispatchEvent(Long ambulanceId, String patientId, String location) {
        this.ambulanceId = ambulanceId;
        this.patientId = patientId;
        this.location = location;
    }

    public Long getAmbulanceId() {
        return ambulanceId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getLocation() {
        return location;
    }
}
