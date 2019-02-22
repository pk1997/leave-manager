package com.hashedin.hu;

import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorService {
    public LeaveAccrual getAccrual() {
        return accrual;
    }

    public void setAccrual(LeaveAccrual accrual) {
        this.accrual = accrual;
    }

    private LeaveAccrual accrual = new LeaveAccrual();
    public void scheduler(Employee empoyee)
    {
        ScheduledExecutorService execService
                =   Executors.newScheduledThreadPool(5);
        execService.scheduleAtFixedRate(()->{
            accrual.addLeavesMonthly(empoyee, LocalDate.now());
        }, 0, 30, TimeUnit.DAYS);

    }
}
