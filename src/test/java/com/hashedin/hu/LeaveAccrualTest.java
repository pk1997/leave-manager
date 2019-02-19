package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LeaveAccrualTest {
    Empoyee e1 = new Empoyee("e1",1,0,Gender.FEMALE, LocalDate.of(2019,1,16));
    LeaveAccrual accrual = new LeaveAccrual();
    LeaveManager manager = new LeaveManager();
    @Test
    public void testAddOnlyOneLeaveIfEmployeeJoinedAfter15(){
        accrual.addLeavesMonthly(e1,LocalDate.of(2019,2,1));
        assertEquals(3,e1.total_no_of_leaves);
    }
    @Test
    public void testMaternityLeaveUpdating()
    {
        LeaveRequest request = new LeaveRequest(e1,LocalDate.of(2019,3,10),LocalDate.of(2019,9,1),true);
        request.types = LeaveTypes.MATERNITY;
        LeaveResponse response = manager.ApplyLeave(request);
        accrual.addLeavesMonthly(e1,LocalDate.of(2019,8,1));
        assertEquals(e1.total_no_of_leaves,6);
    }
    @Test
    public void testMaternityLeaveUpdatingCheckAfterHoliday()
    {
        LeaveRequest request = new LeaveRequest(e1,LocalDate.of(2019,3,10),LocalDate.of(2019,9,1),true);
        request.types = LeaveTypes.MATERNITY;
        LeaveResponse response = manager.ApplyLeave(request);
        accrual.addLeavesMonthly(e1,LocalDate.of(2019,10,1));
        assertEquals(10,e1.total_no_of_leaves);
    }

}