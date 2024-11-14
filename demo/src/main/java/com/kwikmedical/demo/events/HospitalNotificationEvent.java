package com.kwikmedical.demo.events;

public class HospitalNotificationEvent {
    private Long hospitalId;
    private String patientId;

    public HospitalNotificationEvent(Long hospitalId, String patientId) {
        this.hospitalId = hospitalId;
        this.patientId = patientId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public String getPatientId() {
        return patientId;
    }
}
