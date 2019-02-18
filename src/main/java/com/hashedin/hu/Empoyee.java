package com.hashedin.hu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empoyee {
    public Empoyee(String employee_name, int employee_id, int no_of_leaves_avilable,Gender gender) {
        this.employee_name = employee_name;
        this.employee_id = employee_id;
        this.no_of_leaves_avilable = no_of_leaves_avilable;
        this.gender = gender;
    }

    String employee_name;
    int employee_id;
    int no_of_leaves_avilable;
    Gender gender;
    ArrayList<LocalDate> fromDate = new ArrayList<LocalDate>();
    ArrayList<LocalDate> toDate = new ArrayList<LocalDate>();
    CompOff compOff = new CompOff();

    public String getEmployee_name() {
        return employee_name;
    }
    public void addCompoff(LocalDate date)
    {
        this.compOff.availableLeaves+=1;
        this.compOff.setWorkedOn(date);
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

    public int getNo_of_leaves_available() {
        return no_of_leaves_avilable;
    }

    public void setNo_of_leaves_avilable(int no_of_leaves_available) {
        this.no_of_leaves_avilable = no_of_leaves_available;
    }
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

}
