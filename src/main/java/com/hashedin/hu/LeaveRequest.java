package com.hashedin.hu;

import java.time.LocalDate;

public class LeaveRequest {
    int id;
    LocalDate startDate;
    LocalDate endDate;
    boolean blanketCoverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LeaveRequest(int id, LocalDate startDate, LocalDate endDate,boolean blanketCoverage) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.blanketCoverage = blanketCoverage;
    }
}
