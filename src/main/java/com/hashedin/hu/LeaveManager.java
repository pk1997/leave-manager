package com.hashedin.hu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LeaveManager {
    LeaveAccrual accural = new LeaveAccrual();
    public int leaves_available = 0;
    private long no_of_days = 0;
    EmployeeStore employeeStore = new EmployeeStore();

    public LeaveResponse ApplyLeave(LeaveRequest leave)
    {
        accural.addLeavesMonthly(leave.empoyee,leave.getRequestedDate());

        leaves_available = leave.empoyee.total_no_of_leaves-leave.empoyee.no_of_leaves_taken;
        isDateValid(leave);
        LeaveResponse response= new LeaveResponse(LeaveStatus.REJECTED,"");
        if(checkForOverlap(leave))
        {
            response.setStatus(LeaveStatus.REJECTED);
            response.setComment("duplicate request");
            return response;
        }
        if(leave.types == LeaveTypes.MATERNITY)
        {
            if(checkMaternityLeaves(leave))
            {
                leave.empoyee.setMaternity_leave_from(leave.startDate);
                leave.empoyee.setMaternity_leave_till(leave.endDate);
                leave.empoyee.no_of_maternity_leaves_taken+=1;
                response.setStatus(LeaveStatus.APPROVED);
                response.setComment("enjoy the maternity leave");
                leave.empoyee.addLeave(leave.startDate,leave.endDate);
                return response;
            }
            else {
                response.setStatus(LeaveStatus.REJECTED);
                return  response;
            }
        }
        else if(leave.types == LeaveTypes.PATERNITY)
        {
            if(checkForPaternityLeaves(leave))
            {
                response.setStatus(LeaveStatus.APPROVED);
                leave.empoyee.addLeave(leave.startDate,leave.endDate);
                return response;
            }
            response.setStatus(LeaveStatus.REJECTED);

        }
        else if(leave.types == LeaveTypes.COMP_OFF)
        {
            if(handleCompOff(leave))
            {
                response.setStatus(LeaveStatus.APPROVED);
                leave.empoyee.addLeave(leave.startDate,leave.endDate);
                return response;
            }
            response.setStatus(LeaveStatus.REJECTED);
            return response;
        }
        if (checkForAvailableLeaves(leave))
        {
            response.setStatus(LeaveStatus.APPROVED);
            leave.empoyee.addLeave(leave.startDate,leave.endDate);
            leave.empoyee.no_of_leaves_taken+=1;
        }
        return response;
    }


    private boolean handleCompOff(LeaveRequest leave) {
        ArrayList <LocalDate> publicHolidays = new ArrayList<>();
        publicHolidays.add(LocalDate.of(2019,1,25));
        no_of_days = getWorkingDaysBetweenTwoDays(leave.startDate,leave.endDate,publicHolidays);
        if(leave.empoyee.compOff.availableLeaves>0){
        ArrayList<LocalDate> date;
            date = leave.empoyee.compOff.getWorkedOn();
            if (leave.empoyee.compOff.availableLeaves > 0) {
                for (int i = 0; i < date.size(); i++) {
                    if ((ChronoUnit.DAYS.between(date.get(i), leave.startDate) < 30) &&(ChronoUnit.DAYS.between(leave.startDate, leave.endDate) <= leave.empoyee.compOff.availableLeaves)){
                        date.remove(i);
                        leave.empoyee.compOff.availableLeaves -= 1;
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean checkForOverlap(LeaveRequest leave) {
        ArrayList<LocalDate> from = leave.empoyee.getLeaveFrom();
        ArrayList<LocalDate> to = leave.empoyee.getLeaveTo();
        for (int i=0;i< from.size();i++) {
            if(validateOverlap(from.get(i), to.get(i),leave))
            {
                return true;
            }

        }
        return false;
    }

    private boolean validateOverlap(LocalDate from, LocalDate to,LeaveRequest leave) {
        if ((from.isBefore(leave.startDate) && to.isAfter(leave.endDate)))
        {
            return true;
        }
        else if(leave.startDate.isBefore(to) && leave.endDate.isAfter(to))
        {
            return true;
        }
        else if(leave.endDate == to || leave.startDate == from || leave.endDate == from || leave.startDate == to)
        {
            return true;
        }
        else if(leave.startDate.isBefore(from) && leave.endDate.isAfter(from))
        {
            return true;
        }
        return  false;
    }

    private boolean checkForPaternityLeaves(LeaveRequest leave) {
        no_of_days = ChronoUnit.DAYS.between(leave.startDate, leave.endDate);
        if(leave.empoyee.gender == Gender.MALE && no_of_days <= 10){
            return true;
        }
        return false;
    }

    private boolean checkMaternityLeaves(LeaveRequest leave) {
        no_of_days = ChronoUnit.DAYS.between(leave.startDate, leave.endDate);
        if (no_of_days > 180 || leave.empoyee.gender != Gender.FEMALE || leave.empoyee.no_of_maternity_leaves_taken > 2)
        {
            return false;
        }
        return true;

    }

    private boolean checkForAvailableLeaves (LeaveRequest leave)
        {
             ArrayList <LocalDate> publicHolidays = new ArrayList<>();
            publicHolidays.add(LocalDate.of(2019,1,25));
            if(!leave.blanketCoverage) {
                no_of_days = getWorkingDaysBetweenTwoDays(leave.startDate,leave.endDate,publicHolidays);
            }
            else
            {
                no_of_days = ChronoUnit.DAYS.between(leave.startDate, leave.endDate);
            }
            if(no_of_days >= leaves_available)
            {
                return false;
            }
            return true;
        }
    public void isDateValid(LeaveRequest leave)
    {
        if (leave.endDate.isBefore(leave.startDate))
        {
            throw new IllegalArgumentException("Illegel dates please check");
        }

    }
    private int getWorkingDaysBetweenTwoDays(LocalDate from,LocalDate to, ArrayList<LocalDate> publicHolidays) {
        int workingDays=0;
        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                    || publicHolidays.contains(date)))
            {
                workingDays++;
            }
        }
        return workingDays;
    }



}

