package com.kwikmedical.demo.events;

public class RescueRequestEvent {
    private String patientId;
    private String location;

    public RescueRequestEvent(String patientId, String location) {
        this.patientId = patientId;
        this.location = location;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getLocation() {
        return location;
    }
}
