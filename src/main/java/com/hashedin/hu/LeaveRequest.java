package com.hashedin.hu;

import java.time.LocalDate;

public class LeaveRequest {
    Empoyee empoyee;
    LocalDate requestedDate;

    {
        requestedDate = LocalDate.now();
    }
    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    LocalDate startDate;
    LocalDate endDate;
    boolean blanketCoverage;
    LeaveTypes types;

    public Empoyee getEmployee() {
        return empoyee;
    }

    public void setId(Empoyee empoyee) {
        this.empoyee = empoyee;
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
    public void setTypes(LeaveTypes type)
    {
        this.types = type;
    }
    public LeaveRequest(Empoyee empoyee, LocalDate startDate, LocalDate endDate,boolean blanketCoverage) {
        this.empoyee=empoyee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.blanketCoverage = blanketCoverage;
        this.types=LeaveTypes.NORMAL;
        if(this.requestedDate.isAfter(this.startDate))
        {
            throw new IllegalArgumentException("Startdate before requested Date");
        }
    }

}
