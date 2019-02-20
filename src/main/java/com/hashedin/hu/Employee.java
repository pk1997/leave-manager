//Employee class to store data of employee details
package com.hashedin.hu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee {
    public Employee(String name, int id, int no_of_leaves_avilable, Gender gender, String date) {
        this.name = name;
        this.id = id;
        this.total_no_of_leaves = no_of_leaves_avilable;
        this.gender = gender;
        this.joining_date = LocalDate.parse(date);
    }
//properties of employee
    String name;
    int id;
    int no_of_leaves_taken = 0;
    int total_no_of_leaves;
    Gender gender;
    ArrayList<LocalDate> fromDate = new ArrayList<LocalDate>();//if leave is taken store starting date here
    ArrayList<LocalDate> toDate = new ArrayList<LocalDate>();//if leave is taken store leave end date here
    CompOff compOff = new CompOff();//Add any compoff here
    LocalDate joining_date; //Joining date of employee for leave calculation
    LocalDate leavesLastResetOn = LocalDate.of(2019,1,1);
    int no_of_maternity_leaves_taken;
    LocalDate maternity_leave_from;
    LocalDate Maternity_leave_till;
    OptionaLeaves optionaLeaves = new OptionaLeaves();

    public LocalDate getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(LocalDate joining_date) {
        this.joining_date = joining_date;
    }




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

    public String getName() {
        return name;
    }
    public void addCompoff(LocalDateTime login,LocalDateTime logout)
    {
        this.compOff.setWorkedOn(login,logout);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.joining_date = of;
    }
}