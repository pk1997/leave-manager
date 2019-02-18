package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LeaveManagerTest {

    @Test
    public void testValidateLeave() {
        LeaveManager manager = new LeaveManager();
        LeaveRequest leave = new LeaveRequest(1, LocalDate.of(2019,1,1), LocalDate.of(2019,1,4),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDates(){
        LeaveManager manager = new LeaveManager();
        LeaveRequest leave = new LeaveRequest(1, LocalDate.of(2019,1,4), LocalDate.of(2019,4,1),false);
        LeaveResponse response = manager.ApplyLeave(leave);
    }
    @Test
    public void checkForWeekDays(){
        LeaveManager manager = new LeaveManager();
        LeaveRequest leave = new LeaveRequest(1,LocalDate.of(2019,1,4),LocalDate.of(2019,1,7),false);
        manager.leaves_available=2;
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);

    }
    @Test
    public void checkForBlanketCoverage(){
        LeaveManager manager = new LeaveManager();
        LeaveRequest leave = new LeaveRequest(1,LocalDate.of(2019,1,4),LocalDate.of(2019,1,7),true);
        manager.leaves_available=5;
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(2,manager.leaves_available);

    }
}