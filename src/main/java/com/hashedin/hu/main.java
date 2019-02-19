package com.hashedin.hu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String args[])
    {
        LocalDate startDate = LocalDate.of(2019,1,6);
        LocalDate endDate = LocalDate.of(2019,6,6);

        long monthsBetween = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1),
                endDate.withDayOfMonth(1))+1;
        System.out.println(monthsBetween);
    }



    }

