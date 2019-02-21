//Checks whether leave application is to be approved or rejected
package com.hashedin.hu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
@Service
public class LeaveManager {
    //creates instance of accural for getting total number of leaves employee has


    LeaveAccrual accural = new LeaveAccrual();
    //initialization of number of leaves available to employee
    public int leaves_available = 0;
    private long no_of_days = 0;


    public LeaveResponse ApplyLeave(LeaveRequest leave)
    {   //Credit check the number of leaves to employee until the requested date
        accural.addLeavesMonthly(leave.empoyee,leave.getRequestedDate());
        //calculate the number of leaves employee has
        leaves_available = leave.empoyee.total_no_of_leaves-leave.empoyee.no_of_leaves_taken;
        isDateValid(leave);
        LeaveResponse response= new LeaveResponse(LeaveStatus.REJECTED,"");
        if(checkForOverlap(leave))
        {   //if leave is duplicate i.e employee has already applied for leave on the same date reject the leave
            response.setStatus(LeaveStatus.REJECTED);
            response.setComment("duplicate request");
            return response;
        }
        if(leave.types == LeaveTypes.MATERNITY)
        {
            if(checkMaternityLeaves(leave))
            {//save the details of maternity leave into employee object
                leave.empoyee.no_of_maternity_leaves_taken+=1;
                leave.empoyee.setMaternity_leave_from(leave.startDate);
                leave.empoyee.setMaternity_leave_till(leave.endDate);
                //increase the number of maternity leave employee has taken
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
        //check for paternity leave
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
        //check if holiday is of type COMP_OFF
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
        else if(leave.types == LeaveTypes.SABBATICAL)
        {
            if(handleSabbatical(leave))
            {
                response.setStatus(LeaveStatus.APPROVED);
            }
            else
            {
                response.setStatus(LeaveStatus.REJECTED);
            }
            return response;
        }
        //if leave is of Normal leave
        if (checkForAvailableLeaves(leave))
        {
            response.setStatus(LeaveStatus.APPROVED);
            leave.empoyee.addLeave(leave.startDate,leave.endDate);
            leave.empoyee.no_of_leaves_taken+=1;
        }
        return response;
    }

    private boolean handleSabbatical(LeaveRequest leave) {
        if(ChronoUnit.YEARS.between(leave.empoyee.getJoining_date(), leave.startDate) >=2
        && ChronoUnit.DAYS.between(leave.startDate,leave.endDate) > 45
        && ChronoUnit.MONTHS.between(leave.startDate,leave.endDate) <3)
        {
            return true;

        }
        return false;
    }

    private boolean checkForOptionalLeaves(LeaveRequest leave) {
        for (LocalDate date = leave.startDate; date.isBefore(leave.endDate); date = date.plusDays(1)) {
            if(leave.empoyee.optionaLeaves.checkForOptionalLeaves(date))
            {
                return true;
            }
        }

        return false;
    }


    private boolean handleCompOff(LeaveRequest leave) {
        //check the number of days that employee is applying for compoff
        no_of_days = getWorkingDaysBetweenTwoDays(leave.startDate,leave.endDate);
        //check if employee has compoff leaves in his account
        if(leave.empoyee.compOff.availableLeaves>0){
        int availableCompoffs = leave.empoyee.compOff.avilableCompoffs(leave.startDate);
        if (availableCompoffs >= no_of_days)
        {
            return true;
        }
        return false;

        }
        return false;
    }

    private boolean checkForOverlap(LeaveRequest leave) {
        //collect the leaves thay employee has already applied for
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
        /* If the current applied leave is before the leave start date and current applied leave
        * enddate is after the leave end date then overlap will occur*/
        if ((from.isBefore(leave.startDate) && to.isAfter(leave.endDate)))
        {
            return true;
        }
        /*If the current applied leave start date is before the stored leave startdate
        and current leave endaate os after leave end date then overlap occurs
        */
        else if(leave.startDate.isBefore(to) && leave.endDate.isAfter(to))
        {
            return true;
        }
        /*If current date is equal to any of the db leave dates then overlap will occur
        **/
        else if(leave.endDate == to || leave.startDate == from || leave.endDate == from || leave.startDate == to)
        {
            return true;
        }
        /*If current leave applied date is before start and enddate is after start then overlap occurs
        * */
        else if(leave.startDate.isBefore(from) && leave.endDate.isAfter(from))
        {
            return true;
        }
        return  false;
    }

    private boolean checkForPaternityLeaves(LeaveRequest leave) {
        no_of_days = getWorkingDaysBetweenTwoDays(leave.startDate, leave.endDate);
        if(leave.empoyee.gender == Gender.MALE && no_of_days <= 10){
            return true;
        }
        return false;
    }

    private boolean checkMaternityLeaves(LeaveRequest leave) {
        no_of_days = ChronoUnit.DAYS.between(leave.startDate, leave.endDate);
        if(leave.empoyee.no_of_maternity_leaves_taken >0)
        {
            if(ChronoUnit.DAYS.between(leave.empoyee.getMaternity_leave_till(), leave.startDate) <180 )
            {
                return  false;
            }
        }

        //if the employee is male or paternity leave is more than 180 days then reject the maternity leave
        if (no_of_days > 180 || leave.empoyee.gender != Gender.FEMALE || leave.empoyee.no_of_maternity_leaves_taken > 2
                ||ChronoUnit.DAYS.between(leave.empoyee.joining_date, leave.startDate) <180)
        {
            return false;
        }
        return true;

    }

    private boolean checkForAvailableLeaves (LeaveRequest leave)
        {//if leave has no blanket coverage then get the no of working days between the leaves applied

            if(!leave.blanketCoverage) {
                no_of_days = getWorkingDaysBetweenTwoDays(leave.startDate,leave.endDate);
            }
           /* if(checkForOptionalLeaves(leave))
            {
                no_of_days -=1;
            }*/

            else
            {
                no_of_days = getWorkingDaysBetweenTwoDays(leave.startDate,leave.endDate);
            }
            /*if number of days the employee is applying leave for is greater
            than the employee has in his account then reject the leave
             */
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
    private int getWorkingDaysBetweenTwoDays(LocalDate from,LocalDate to) {
        //get working days between two dates
        int workingDays=0;
        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            //if date is saturday or sundau or any other public holiday then dont add it to working day
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                    || isPublicHoliday(date)))
            {
                workingDays++;
            }
        }
        return workingDays;
    }

    private boolean isPublicHoliday(LocalDate date) {
        //check for public hoidays
        ArrayList <LocalDate> publicHolidays = new ArrayList<>();
        publicHolidays.add(LocalDate.of(2019,1,25));
        publicHolidays.add(LocalDate.of(2019,8,15));
        publicHolidays.add(LocalDate.of(2019,12,25));
        publicHolidays.add(LocalDate.of(2019,10,2));
        return publicHolidays.contains(date);
    }


}

