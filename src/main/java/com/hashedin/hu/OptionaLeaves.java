package com.hashedin.hu;

import java.time.LocalDate;
import java.util.ArrayList;

public class OptionaLeaves {
    private int noOfOptionalLeavesTaken = 0;
    private ArrayList<LocalDate> optionalLeaves = new ArrayList<>();

    public int getNoOfOptionalLeavesTaken() {
        return noOfOptionalLeavesTaken;
    }

    public void setNoOfOptionalLeavesTaken(int noOfOptionalLeavesTaken) {
        this.noOfOptionalLeavesTaken = noOfOptionalLeavesTaken;
    }

    public ArrayList<LocalDate> getOptionalLeaves() {
        return optionalLeaves;
    }

    public void setOptionalLeaves(ArrayList<LocalDate> optionalLeaves) {
        this.optionalLeaves = optionalLeaves;
    }

    public boolean checkForOptionalLeaves(LocalDate date)
    {
        optionalLeaves.add(LocalDate.of(2019,8,12));
        optionalLeaves.add(LocalDate.of(2019,3,19));
     if(this.noOfOptionalLeavesTaken == 0 && optionalLeaves.contains(date))
     {
         this.noOfOptionalLeavesTaken =1;
         return true;
     }
     return false;
    }
}
