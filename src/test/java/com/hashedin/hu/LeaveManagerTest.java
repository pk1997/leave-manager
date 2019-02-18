package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LeaveManagerTest {
    private LeaveManager manager = new LeaveManager();

    @Test
    public void testValidateLeave() {
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",1,10, Gender.MALE);
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,1,1), LocalDate.of(2019,1,4),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testLeaveMoreThanEmpHas() {
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",1,1, Gender.MALE);
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,1,1), LocalDate.of(2019,1,4),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDates(){
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",2,12,Gender.MALE);
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,1,4), LocalDate.of(2019,4,1),false);
        LeaveResponse response = manager.ApplyLeave(leave);
    }
    @Test
    public void checkForWeekDays(){
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp2",3,20,Gender.FEMALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,4),LocalDate.of(2019,1,7),false);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(19,e.no_of_leaves_avilable);

    }
    @Test
    public void checkForBlanketCoverage(){
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp3",4,20,Gender.FEMALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,4),LocalDate.of(2019,1,7),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(17,e.no_of_leaves_avilable);
    }
    @Test
    public void testMaternityLeave(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.FEMALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,1),LocalDate.of(2019,5,5),true);
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testMaternityLeaveMoreThan6Mos(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.FEMALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,1),LocalDate.of(2019,7,5),true);
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }
    @Test
    public void testMaternityLeaveForMale(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.MALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,1),LocalDate.of(2019,4,5),true);
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }
    @Test
    public void testPaternityLeaveFor(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.MALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,1),LocalDate.of(2019,1,5),true);
        leave.setTypes(LeaveTypes.PATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testPaternityLeaveForFemale(){
        Empoyee e = new Empoyee("emp4",5,20,Gender.FEMALE);
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,1,1),LocalDate.of(2019,1,5),true);
        leave.setTypes(LeaveTypes.PATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testValidateLeaveDuplicate() {
        LeaveManager manager = new LeaveManager();
        Empoyee e = new Empoyee("emp1",1,10, Gender.MALE);
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,1,1), LocalDate.of(2019,1,4),true);
        LeaveRequest leave2 = new LeaveRequest(e, LocalDate.of(2019,1,2), LocalDate.of(2019,1,3),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        LeaveResponse res2=manager.ApplyLeave(leave2);
        assertEquals(LeaveStatus.REJECTED,res2.status);
    }
@Test
    public void testApplyLeaveWithoutCompoff(){
    Empoyee e = new Empoyee("emp1",1,10, Gender.MALE);
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,01,1),LocalDate.of(2019,1,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.REJECTED);
}
@Test
    public void testAddCompOffAndApply(){
    Empoyee e = new Empoyee("emp1",1,10, Gender.MALE);
    e.addCompoff(LocalDate.of(2019,1,1));
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,01,1),LocalDate.of(2019,1,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.APPROVED);
}
@Test
    public void testAddCompoffAndRedeemAfterMonth()
{
    Empoyee e = new Empoyee("emp1",1,10, Gender.MALE);
    e.addCompoff(LocalDate.of(2019,1,1));
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,4,1),LocalDate.of(2019,4,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.REJECTED);
}
}