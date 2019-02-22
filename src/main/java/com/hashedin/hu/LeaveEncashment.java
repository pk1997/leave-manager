package com.hashedin.hu;
//Assuming that this script will be run yearly
//Date is assumed to be 01/01/2020
import java.time.LocalDate;

public class LeaveEncashment {
    private LeaveAccrual accrual;
    public void encashLeaves(Employee e)
    {
        LocalDate d = LocalDate.of(2020,01,01);
        accrual.addLeavesMonthly(e,d);
        int leavesLeft =e.getTotalNoOfLeaves()+e.getLeavesCarriedFromLastYear()-e.getNoOfLeavesTaken();
        if(leavesLeft > 12){
            int cashableLeaves = leavesLeft - 12;
            e.setLeavesCarriedFromLastYear(12);
                    }
        else
        {
            e.setLeavesCarriedFromLastYear(leavesLeft);

        }
        e.setTotalNoOfLeaves(0);
        e.setNoOfLeavesTaken(0);
        e.setLeavesLastResetOn(d);

    }
}
