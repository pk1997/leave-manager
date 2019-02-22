package com.hashedin.hu;
//create leave applications using this class

import com.hashedin.hu.models.LeaveTypes;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean blanketCoverage;
    private LeaveTypes types;
    @ManyToOne
    @JoinColumn(name = "Employee")
    Employee empoyee;
    private int empId;
    private LocalDate requestedDate;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }
//store the date o


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBlanketCoverage() {
        return blanketCoverage;
    }

    public void setBlanketCoverage(boolean blanketCoverage) {
        this.blanketCoverage = blanketCoverage;
    }

    public LeaveTypes getTypes() {
        return types;
    }

    public Employee getEmpoyee() {
        return empoyee;
    }

    public void setEmpoyee(Employee empoyee) {
        this.empoyee = empoyee;
    }

    //store the date on which leave was requested default is todays date


    {
        requestedDate = LocalDate.now();
    }
    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }



    public Employee getEmployee() {
        return empoyee;
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

    public void setMaternityLeave()
    {
        this.types = LeaveTypes.MATERNITY;
        this.endDate = this.startDate.plusDays(180);
    }

    public LeaveRequest(Employee empoyee, LocalDate startDate, LocalDate endDate, boolean blanketCoverage) {
        this.empoyee=empoyee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.blanketCoverage = blanketCoverage;
        this.types=LeaveTypes.NORMAL;
        //if requested date is before the date leave has been applied then throw an exception
        if(this.requestedDate.isAfter(this.startDate))
        {
            throw new IllegalArgumentException("Startdate before requested Date");
        }
    }

}
