package com.hashedin.hu;
import java.time.LocalDate;

public class main {
    public static void main(String args[])
    {
        LeaveManager manager = new LeaveManager();
        LeaveRequest leave = new LeaveRequest(1, LocalDate.of(2019,1,4), LocalDate.of(2019,1,7),true);
        LeaveResponse response= manager.ApplyLeave(leave);
    }
}
