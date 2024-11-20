package com.kwikmedical.demo.events;

public class RescueOperationUpdatedEvent {
    private final Long operationId;
    private final String status;

    public RescueOperationUpdatedEvent(Long operationId, String status) {
        this.operationId = operationId;
        this.status = status;
    }

    public Long getOperationId() {
        return operationId;
    }

    public String getStatus() {
        return status;
    }
}

