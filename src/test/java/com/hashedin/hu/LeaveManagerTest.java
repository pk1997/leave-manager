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
        Employee e = new Employee("emp1", (long) 1,10, Gender.MALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,3,1), LocalDate.of(2019,3,4),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testLeaveMoreThanEmpHas() {
        LeaveManager manager = new LeaveManager();
        Employee e = new Employee("emp1", (long) 1,1, Gender.MALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,3,1), LocalDate.of(2019,3,10),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }

    @Test
    public void checkForWeekDays(){
        LeaveManager manager = new LeaveManager();
        Employee e = new Employee("emp2", (long) 3,20,Gender.FEMALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,4),false);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(1,e.no_of_leaves_taken);

    }
    @Test
    public void testMaternityLeave(){
        Employee e = new Employee("emp4", (long) 5,20,Gender.FEMALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,7,1),null,true);
        leave.setMaternityLeave();
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }

    @Test
    public void testMaternityLeaveForMale(){
        Employee e = new Employee("emp4", (long) 5,20,Gender.MALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,4,5),true);
        leave.setRequestedDate(LocalDate.of(2019,1,1));
        leave.setTypes(LeaveTypes.MATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.REJECTED);
    }
    @Test
    public void testPaternityLeaveForMale(){
        Employee e = new Employee("emp4", (long) 5,20,Gender.MALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,5),true);
        leave.setTypes(LeaveTypes.PATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testPaternityLeaveForFemale(){
        Employee e = new Employee("emp4", (long) 5,20,Gender.FEMALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,5),true);
        leave.setTypes(LeaveTypes.PATERNITY);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(response.status,LeaveStatus.APPROVED);
    }
    @Test
    public void testValidateLeaveDuplicate() {
        LeaveManager manager = new LeaveManager();
        Employee e = new Employee("emp1", (long) 1,10, Gender.MALE,"2019-01-01");
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019,3,1), LocalDate.of(2019,3,4),true);
        LeaveRequest leave2 = new LeaveRequest(e, LocalDate.of(2019,3,2), LocalDate.of(2019,3,3),true);
        LeaveResponse response = manager.ApplyLeave(leave);
        LeaveResponse res2=manager.ApplyLeave(leave2);
        assertEquals(LeaveStatus.REJECTED,res2.status);
    }
@Test
    public void testApplyLeaveWithoutCompoff(){
    Employee e = new Employee("emp1", (long) 1,10, Gender.MALE,"2019-01-01");
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,3,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.REJECTED);
}
@Test
    public void testAddCompOffAndApply(){
    Employee e = new Employee("emp1", (long) 1,10, Gender.MALE,"2019-01-01");
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
    Employee e = new Employee("emp1", (long) 1,10, Gender.MALE,"2019-01-01");
    e.addCompoff(LocalDateTime.of(2019, Month.FEBRUARY,16,9,00),
            LocalDateTime.of(2019,Month.FEBRUARY,16,18,00));
    LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,4,1),LocalDate.of(2019,4,2),false);
    leave.setTypes(LeaveTypes.COMP_OFF);
    LeaveResponse response = manager.ApplyLeave(leave);
    assertEquals(response.status,LeaveStatus.REJECTED);
}
@Test(expected = IllegalArgumentException.class)
    public void testInvalidDates() {
    Employee e = new Employee("emp1", (long) 1, 10, Gender.MALE, "2019-01-01");
    LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019, 3, 10), LocalDate.of(2019, 3, 1), false);
    LeaveResponse response = manager.ApplyLeave(leave);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testApplyLeaveForPastDate(){
        Employee e = new Employee("emp1", (long) 1, 10, Gender.MALE, "2019-01-01");
        LeaveRequest leave = new LeaveRequest(e, LocalDate.of(2019, 2, 10), LocalDate.of(2019, 2, 11), false);
        LeaveResponse response= manager.ApplyLeave(leave);
    }
    @Test
    public void testForOpitonalLeave()
    {
        Employee e = new Employee("emp1", (long) 1, 0, Gender.MALE, "2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,8,11),LocalDate.of(2019,8,13),false);
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(1,leave.empoyee.no_of_leaves_taken);
    }
    @Test
    public void testEmployeeIsApplyingForTwoOptionalLeaves()
    {
        Employee e = new Employee("emp1", (long) 1, 0, Gender.MALE, "2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,8,11),LocalDate.of(2019,8,13),false);
        LeaveResponse response = manager.ApplyLeave(leave);
        LeaveRequest leave2 = new LeaveRequest(e,LocalDate.of(2019,3,19),LocalDate.of(2019,3,19),false);
        LeaveResponse response1 = manager.ApplyLeave(leave2);
        assertEquals(2,e.no_of_leaves_taken);
    }
    @Test
    public void tesApplyForSabbatical()
    {
        Employee e = new Employee("emp1", (long) 1, 0, Gender.MALE, "2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2021,3,1),LocalDate.of(2021,5,1),false);
        leave.types = LeaveTypes.SABBATICAL;
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(LeaveStatus.APPROVED,response.status);
    }
    @Test
    public void testApplyForSabbaticalWothout2YearsOfExperience()
    {
        Employee e = new Employee("emp1", (long) 1, 0, Gender.MALE, "2019-01-01");
        LeaveRequest leave = new LeaveRequest(e,LocalDate.of(2019,3,1),LocalDate.of(2019,5,1),false);
        leave.types = LeaveTypes.SABBATICAL;
        LeaveResponse response = manager.ApplyLeave(leave);
        assertEquals(LeaveStatus.REJECTED,response.status);
    }

}