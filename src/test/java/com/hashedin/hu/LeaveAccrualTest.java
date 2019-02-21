package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LeaveAccrualTest {
    Employee e1 = new Employee("e1", (long) 1,0,Gender.FEMALE, "2019-01-16");
    Employee e2 = new Employee("e1", (long) 1,0,Gender.FEMALE, "2019-01-10");

    LeaveAccrual accrual = new LeaveAccrual();
    LeaveManager manager = new LeaveManager();

    @Test
    public void testAddOnlyOneLeaveIfEmployeeJoinedAfter15(){
        accrual.addLeavesMonthly(e1,LocalDate.of(2019,2,1));
        assertEquals(1,e1.total_no_of_leaves);
    }
    @Test
    public void testMaternityLeaveUpdating()
    {
        LeaveRequest request = new LeaveRequest(e2,LocalDate.of(2019,7,10),null,true);
        request.setMaternityLeave();
        LeaveResponse response = manager.ApplyLeave(request);
        accrual.addLeavesMonthly(e2,LocalDate.of(2019,10,1));
        assertEquals(e2.total_no_of_leaves,14);
    }
    @Test
    public void testMaternityLeaveUpdatingCheckAfterHoliday()
    {
        LeaveRequest request = new LeaveRequest(e1,LocalDate.of(2019,7,10),null,true);
        request.setMaternityLeave();
        LeaveResponse response = manager.ApplyLeave(request);
        accrual.addLeavesMonthly(e1,LocalDate.of(2019,10,1));
        assertEquals(18,e1.total_no_of_leaves);
    }
    @Test
    public void testForLeaveUpdating()
    {
        accrual.addLeavesMonthly(e2,LocalDate.of(2019,2,1));
        assertEquals(2,e2.total_no_of_leaves);
    }
    @Test
    public void testForSchedulerWorking()
    {
        ExecutorService service = new ExecutorService();
        accrual.addLeavesMonthly(e1,LocalDate.now());
        assertEquals(1,e1.total_no_of_leaves);
    }

}