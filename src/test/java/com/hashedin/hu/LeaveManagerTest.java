package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class LeaveManagerTest {
    private LeaveManager manager = new LeaveManager();

    @Test
    public void testValidateLeave() {
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",1,10, Gender.MALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,3,1), LocalDate.of(2019,3,4),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testLeaveMoreThanEmpHas() {
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",1,1, Gender.MALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,3,1), LocalDate.of(2019,3,10),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }

    @Test
    public void checkForWeekDays(){
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp2",3,20,Gender.FEMALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,4),false);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(1,e.no_of_leaves_taken);

    }
    @Test
    public void testMaternityLeave(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.FEMALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,8,5),true);
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testMaternityLeaveMoreThan6Mos(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.FEMALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,11,5),true);
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }
    @Test
    public void testMaternityLeaveForMale(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.MALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,4,5),true);
        leave.setRequestedDate(LocalDate.of(2019,1,1));
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }
    @Test
    public void testPaternityLeaveForMale(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.MALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,5),true);
        leave.setTypes(LeaveTypes.PATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testPaternityLeaveForFemale(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.FEMALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,5),true);
        leave.setTypes(LeaveTypes.PATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testValidateLeaveDuplicate() {
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",1,10, Gender.MALE,LocalDate.of(2019,1,1));
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,3,1), LocalDate.of(2019,3,4),true);
        LeaveRequest leave2 = new LeaveRequest(e, LocalDate.of(2019,3,2), LocalDate.of(2019,3,3),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        LeaveResponse res2=manager.ApplyLeave(leave2);
        assertEquals(LeaveStatus.REJECTED,res2.status);
    }
@Test
    public void testApplyLeaveWithoutCompoff(){
    Empoyee e = new Empoyee("emp1",1,10, Gender.MALE,LocalDate.of(2019,1,1));
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.REJECTED);
}
@Test
    public void testAddCompOffAndApply(){
    Empoyee e = new Empoyee("emp1",1,10, Gender.MALE,LocalDate.of(2019,1,1));
    e.addCompoff(LocalDateTime.of(2019, Month.FEBRUARY,16,9,00),
            LocalDateTime.of(2019,Month.FEBRUARY,16,20,00));
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.APPROVED);
}
@Test
    public void testAddCompoffAndRedeemAfterMonth()
{
    Empoyee e = new Empoyee("emp1",1,10, Gender.MALE,LocalDate.of(2019,1,1));
    e.addCompoff(LocalDateTime.of(2019, Month.FEBRUARY,16,9,00),
            LocalDateTime.of(2019,Month.FEBRUARY,16,18,00));
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,4,1),LocalDate.of(2019,4,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.REJECTED);
}
@Test(expected = IllegalArgumentException.class)
    public void testInvalidDates() {
    Empoyee e = new Empoyee("emp1", 1, 10, Gender.MALE, LocalDate.of(2019, 1, 1));
    LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019, 3, 10), LocalDate.of(2019, 3, 1), false);
    LeaveResponse response = manager.ApplyLeave(leave);
    }

}