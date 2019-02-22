//Accrual of leaves i.e credit leaves to employee
package com.hashedin.hu;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class LeaveAccrual {
    private long monthsBetween;//contains the no of months employee has worked for
    private int holidaysLeft;//contains number of holidays that employee has earned for working

    {
        holidaysLeft = 0;
    }

    public void addLeavesMonthly(Employee e , LocalDate date)
    {   LocalDate holidayCalculatedFrom;
        if(e.getJoiningDate().isAfter(e.getLeavesLastResetOn()))
        {
            holidayCalculatedFrom = e.getJoiningDate();
        }
        else
        {
            holidayCalculatedFrom = e.getLeavesLastResetOn();
        }

        monthsBetween = ChronoUnit.MONTHS.between(holidayCalculatedFrom.withDayOfMonth(1),
                date.withDayOfMonth(1));
        long noHolidayMonths = checkIfEmployeeHasTakenMaternityLeave(e,date);

        if(checkIfHeHasJoinedAfter15(holidayCalculatedFrom))
        {

            holidaysLeft= (int) (((monthsBetween-noHolidayMonths)*2)-1);
        }

        else
        {
            holidaysLeft = (int) ((monthsBetween-noHolidayMonths) *2);
        }
        e.setTotalNoOfLeaves(holidaysLeft);

    }

    private long checkIfEmployeeHasTakenMaternityLeave(Employee e, LocalDate date) {
        long noOfMonths=0;
        if (e.getNoOfMaternityLeavesTaken() > 0) {
            //if employee has taken maternity leaves
            if (date.getYear() == e.getMaternityLeaveFrom().getYear()) {
                //if maternity leave is taken on same year as of the current date
                if (date.getYear() == e.getMaternityLeaveTill().getYear()) {
                    //if maternity leave ends as of same year of the current date
                    if (date.isAfter(e.getMaternityLeaveTill())) {
                        //if current date is after end of maternity leave
                        noOfMonths = ChronoUnit.MONTHS.between(e.getMaternityLeaveFrom().withDayOfMonth(1),
                                e.getMaternityLeaveTill().withDayOfMonth(1));
                    }
                    //if current date is before end of maternity leave
                    else {
                        noOfMonths = ChronoUnit.MONTHS.between(e.getMaternityLeaveFrom().withDayOfMonth(1),
                                date.withDayOfMonth(1));
                    }
                }
                //if maternity leave doesnt end on same year as of the end of maternity leave
                else {
                    //no of months = maternity start - current date
                     noOfMonths = ChronoUnit.MONTHS.between(e.getMaternityLeaveFrom().withDayOfMonth(1),
                            date.withDayOfMonth(1));
                }
            }

        }
        return noOfMonths;

    }

    private boolean checkIfHeHasJoinedAfter15(LocalDate joiningDate)
    {
        Calendar startCal = Calendar.getInstance();
        Date startDate = Date.from(joiningDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        startCal.setTime(startDate);
        if(startCal.get(Calendar.DAY_OF_MONTH) > 14)
        {
            return true;
        }
        return false;
    }

}
