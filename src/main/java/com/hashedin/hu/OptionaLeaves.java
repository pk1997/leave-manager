package com.hashedin.hu;

import java.time.LocalDate;
import java.util.ArrayList;

public class OptionaLeaves {
    int no_of_optional_leaves_taken = 0;
    ArrayList<LocalDate> optional_leaves = new ArrayList<>();
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
