package com.hashedin.hu;

import java.time.LocalDate;
import java.util.ArrayList;

public class OptionaLeaves {
    int no_of_optional_leaves_taken = 0;
    ArrayList<LocalDate> optional_leaves = new ArrayList<>();

    public int getNo_of_optional_leaves_taken() {
        return no_of_optional_leaves_taken;
    }

    public void setNo_of_optional_leaves_taken(int no_of_optional_leaves_taken) {
        this.no_of_optional_leaves_taken = no_of_optional_leaves_taken;
    }

    public ArrayList<LocalDate> getOptional_leaves() {
        return optional_leaves;
    }

    public void setOptional_leaves(ArrayList<LocalDate> optional_leaves) {
        this.optional_leaves = optional_leaves;
    }

    public boolean checkForOptionalLeaves(LocalDate date)
    {
        optional_leaves.add(LocalDate.of(2019,8,12));
        optional_leaves.add(LocalDate.of(2019,3,19));
     if(this.no_of_optional_leaves_taken == 0 && optional_leaves.contains(date))
     {
         this.no_of_optional_leaves_taken=1;
         return true;
     }
     return false;
    }
}
