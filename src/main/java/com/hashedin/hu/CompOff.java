package com.hashedin.hu;

import java.time.LocalDate;
import java.util.ArrayList;

public class CompOff {

    int availableLeaves;

    {
        availableLeaves = 0;
    }

    ArrayList<LocalDate> WorkedOn = new ArrayList<LocalDate>();

    public int getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(int availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public ArrayList<LocalDate> getWorkedOn() {
        if(WorkedOn.isEmpty()){
            return null;
        }
        return WorkedOn;
    }

    public void setWorkedOn(LocalDate date) {

        WorkedOn.add(date);
    }
}
