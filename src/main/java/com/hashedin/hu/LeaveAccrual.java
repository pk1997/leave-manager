package com.hashedin.hu;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class LeaveAccrual {
    private long monthsBetween;
    private int holidaysLeft;

    {
        holidaysLeft = 0;
    }

    public void addLeavesMonthly(Empoyee e , LocalDate date)
    {
        monthsBetween = ChronoUnit.MONTHS.between(e.JoiningDate.withDayOfMonth(1),
                date.withDayOfMonth(1))+1;
        long no_holiday_months = checkIfEmployeeHasTakenMaternityLeave(e,date);

        if(checkIfHeHasJoinedAfter15(e.JoiningDate))
        {

            holidaysLeft= (int) (((monthsBetween-no_holiday_months)*2)-1);
        }

        else
        {
            holidaysLeft = (int) ((monthsBetween-no_holiday_months) *2);
        }
        e.total_no_of_leaves += holidaysLeft;

    }

    private long checkIfEmployeeHasTakenMaternityLeave(Empoyee e,LocalDate date) {
        long no_of_months=0;
        if (e.no_of_maternity_leaves_taken > 0) {
            if (date.getYear() == e.maternity_leave_from.getYear()) {
                if (date.getYear() == e.getMaternity_leave_till().getYear()) {
                    if (date.isAfter(e.getMaternity_leave_till())) {
                        no_of_months = ChronoUnit.MONTHS.between(e.getMaternity_leave_from().withDayOfMonth(1),
                                e.getMaternity_leave_till().withDayOfMonth(1));
                    }
                    else {
                        no_of_months = ChronoUnit.MONTHS.between(e.maternity_leave_from.withDayOfMonth(1),
                                date.withDayOfMonth(1))+1;
                    }
                }
                else{
                     no_of_months = ChronoUnit.MONTHS.between(e.maternity_leave_from.withDayOfMonth(1),
                            date.withDayOfMonth(1));
                }
            }

        }
        return no_of_months;

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
