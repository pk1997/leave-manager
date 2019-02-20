package com.hashedin.hu;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class CompOffTest {
    LeaveManager manager = new LeaveManager();
    Employee e1 = new Employee("e1",1,0,Gender.MALE, "2019-01-01");
    @Test
    public void testCompOffNotAddedIfLogWorkLessThan8hrs(){
        e1.addCompoff(LocalDateTime.of(2019, Month.FEBRUARY,1,10,10),LocalDateTime.of(2019,Month.FEBRUARY,1,11,1,1));
        assertEquals(0,e1.compOff.getAvailableLeaves());
    }
    @Test
    public void testCompoffShouldNotBeAddedForWeekDays(){
        e1.addCompoff(LocalDateTime.of(2019, Month.FEBRUARY,1,10,10),LocalDateTime.of(2019,Month.FEBRUARY,1,20,1,1));
        assertEquals(0,e1.compOff.getAvailableLeaves());
    }

}