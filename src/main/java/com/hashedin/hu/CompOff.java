package com.hashedin.hu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class CompOff {
    private int availableLeaves;
    private ArrayList<LocalDate> workedOn = new ArrayList<LocalDate>();

    public void setWorkedOn(ArrayList<LocalDate> workedOn) {
        this.workedOn = workedOn;
    }

    {
        availableLeaves = 0;
    }



    public int getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(int availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public ArrayList<LocalDate> getWorkedOn() {
        if(workedOn.isEmpty()){
            return null;
        }
        return workedOn;
    }

    public void setWorkedOn(LocalDateTime login , LocalDateTime logout) {

        long hours = ChronoUnit.HOURS.between(login, logout);
        if(hours > 8 && (login.getDayOfWeek() == DayOfWeek.SATURDAY || login.getDayOfWeek() == DayOfWeek.SUNDAY))
        {
            this.workedOn.add(login.toLocalDate());
            this.availableLeaves +=1;
        }
    }
    public int avilableCompoffs(LocalDate date)
    {
        int avilableCompoffs=0;
        for (LocalDate dates : workedOn)
        {
            if(ChronoUnit.DAYS.between(dates,date) <30)
            {
                avilableCompoffs +=1;
            }
        }
        return avilableCompoffs;
    }
}
