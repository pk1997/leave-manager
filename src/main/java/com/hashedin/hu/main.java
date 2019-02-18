package com.hashedin.hu;
import java.time.LocalDate;
import java.util.Arrays;

public class main {
    public static void main(String args[])
    {   EmployeeStore employeeStore = new EmployeeStore();
        Empoyee e1 = new Empoyee("emp1",1,12,Gender.MALE);
        employeeStore.setEmployee(e1.employee_id,e1);
        Empoyee e2 = new Empoyee("emp2",2,20,Gender.FEMALE);
        employeeStore.setEmployee(e2.employee_id,e2);
        LeaveManager manager = new LeaveManager();
        LeaveRequest leave = new LeaveRequest(e1, LocalDate.of(2019,1,4), LocalDate.of(2019,1,7),true);
        LeaveResponse response= manager.ApplyLeave(leave);




    }
}
