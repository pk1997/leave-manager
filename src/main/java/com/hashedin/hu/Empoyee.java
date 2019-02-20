//Employee class to store data of employee details
package com.hashedin.hu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Empoyee {
    public Empoyee(String employee_name, int employee_id, int no_of_leaves_avilable,Gender gender,LocalDate date) {
        this.employee_name = employee_name;
        this.employee_id = employee_id;
        this.total_no_of_leaves = no_of_leaves_avilable;
        this.gender = gender;
        this.joiningDate = date;
    }
//properties of employee
    String employee_name;
    int employee_id;
    int no_of_leaves_taken = 0;
    int total_no_of_leaves;
    Gender gender;
    ArrayList<LocalDate> fromDate = new ArrayList<LocalDate>();//if leave is taken store starting date here
    ArrayList<LocalDate> toDate = new ArrayList<LocalDate>();//if leave is taken store leave end date here
    CompOff compOff = new CompOff();//Add any compoff here
    LocalDate joiningDate; //Joining date of employee for leave calculation
    LocalDate leavesLastResetOn = LocalDate.of(2019,1,1);
    int no_of_maternity_leaves_taken;
    LocalDate maternity_leave_from;
    LocalDate Maternity_leave_till;

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    OptionaLeaves optionaLeaves = new OptionaLeaves();


    public LocalDate getMaternity_leave_from() {
        return maternity_leave_from;
    }

    public void setMaternity_leave_from(LocalDate maternity_leave_from) {
        this.maternity_leave_from = maternity_leave_from;
    }

    public LocalDate getMaternity_leave_till() {
        return Maternity_leave_till;
    }

    public void setMaternity_leave_till(LocalDate maternity_leave_till) {
        Maternity_leave_till = maternity_leave_till;
    }

    public String getEmployee_name() {
        return employee_name;
    }
    public void addCompoff(LocalDateTime login,LocalDateTime logout)
    {
        this.compOff.setWorkedOn(login,logout);
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getTotal_no_of_leaves() {
        return total_no_of_leaves;
    }

    public void setTotal_no_of_leaves(int total_no_of_leaves) {
        this.total_no_of_leaves = total_no_of_leaves;
    }
    //if leave is approved the store the dates that leave is approved between
    public void addLeave(LocalDate from,LocalDate to)
    {
        this.fromDate.add(from);
        this.toDate.add(to);
    }

    public ArrayList<LocalDate> getLeaveFrom()
    {
        return fromDate;
    }
    public ArrayList<LocalDate> getLeaveTo()
    {
        return toDate;
    }

    public void JoiningDate(LocalDate of) {
        this.joiningDate = of;
    }
}
