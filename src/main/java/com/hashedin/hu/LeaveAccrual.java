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

        if(checkIfHeHasJoinedAfter15(e.JoiningDate))
        {
            holidaysLeft= (int) ((monthsBetween*2)-1);
        }
        else
        {
            holidaysLeft = (int) (monthsBetween *2);
        }
        e.no_of_leaves_available += holidaysLeft;

    }

    private boolean checkIfHeHasJoinedAfter15(LocalDate joiningDate)
    {
        Calendar startCal = Calendar.getInstance();
        Date startDate = Date.from(joiningDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        startCal.setTime(startDate);
        if(startCal.get(Calendar.DAY_OF_MONTH) > 15)
        {
            return true;
        }
        return false;
    }

}
