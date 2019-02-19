package com.hashedin.hu;
import java.time.LocalDate;
import java.util.Arrays;

public class main {
    public static void main(String args[])
    {   Empoyee e = new Empoyee("pavan",1,0,Gender.MALE,LocalDate.of(2019,1,1));
        e.JoiningDate(LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,2,2),LocalDate.of(2019,2,3),false);
        leave.setRequestedDate(LocalDate.of(2019,1,1));
        LeaveManager manager = new LeaveManager();
        LeaveResponse response = manager.ApplyLeave(leave);
        System.out.println(response.status);


    }
}
