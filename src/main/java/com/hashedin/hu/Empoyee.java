package com.hashedin.hu;

public class Empoyee {
    public Empoyee(String employee_name, int employee_id, int no_of_leaves_avilable) {
        this.employee_name = employee_name;
        this.employee_id = employee_id;
        this.no_of_leaves_avilable = no_of_leaves_avilable;
    }

    String employee_name;
    int employee_id;
    int no_of_leaves_avilable;

    public String getEmployee_name() {
        return employee_name;
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

    public int getNo_of_leaves_avilable() {
        return no_of_leaves_avilable;
    }

    public void setNo_of_leaves_avilable(int no_of_leaves_avilable) {
        this.no_of_leaves_avilable = no_of_leaves_avilable;
    }


}
