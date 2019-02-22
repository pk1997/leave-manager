package com.hashedin.hu;

public class LeaveResponse {
    private LeaveStatus status;
    private String comment;

    public LeaveResponse(LeaveStatus status, String comment) {
        this.status = status;
        this.comment = comment;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
