package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LeaveAccrualTest {
    Empoyee e1 = new Empoyee("e1",1,0,Gender.FEMALE, LocalDate.of(2019,1,16));
    LeaveAccrual accrual = new LeaveAccrual();
    @Test
    public void testAddOnlyOneLeaveIfEmployeeJoinedAfter15(){
        accrual.addLeavesMonthly(e1,LocalDate.of(2019,2,1));
        assertEquals(3,e1.no_of_leaves_available);
    }

}