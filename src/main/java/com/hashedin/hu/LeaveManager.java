package com.hashedin.hu;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class LeaveManager {
    public int leaves_available = 10;
    private long no_of_days = 0;
    EmployeeStore employeeStore = new EmployeeStore();
    public LeaveResponse ApplyLeave(LeaveRequest leave)
    {   leaves_available = leave.empoyee.no_of_leaves_avilable;
        isDateValid(leave);
        LeaveResponse response= new LeaveResponse(LeaveStatus.REJECTED,"");
        if(leave.types == LeaveTypes.MATERNITY)
        {
            if(checkMaternityLeaves(leave))
            {
                response.setStatus(LeaveStatus.APPROVED);
                response.setComment("enjoy the maternity leave");
                return response;
            }
            else{
                response.setStatus(LeaveStatus.REJECTED);
                return  response;
            }
        }
        if (checkForAvailableLeaves(leave))
        {
            response.setStatus(LeaveStatus.APPROVED);
            leave.empoyee.no_of_leaves_avilable = (int) (leave.empoyee.no_of_leaves_avilable - no_of_days);
        }
        return response;
    }

    private boolean checkMaternityLeaves(LeaveRequest leave) {
        no_of_days = ChronoUnit.DAYS.between(leave.startDate, leave.endDate);
        if (no_of_days > 180 || leave.empoyee.gender != Gender.FEMALE)
        {
            return false;
        }
        return true;

    }

    private boolean checkForAvailableLeaves (LeaveRequest leave)
        {

            if(!leave.blanketCoverage) {
                no_of_days = getWorkingDaysBetweenTwoDays(leave);
            }
            else
            {
                no_of_days = ChronoUnit.DAYS.between(leave.startDate, leave.endDate);
            }
            if(no_of_days > leaves_available)
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
    private int getWorkingDaysBetweenTwoDays(LeaveRequest leave) {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Date startDate = Date.from(leave.startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(leave.endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        int work_days = 0;
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++work_days;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        return work_days;
    }



}

