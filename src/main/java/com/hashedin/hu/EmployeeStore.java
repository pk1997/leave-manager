package com.hashedin.hu;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class EmployeeStore {
   private ArrayList<Employee> employees = new ArrayList<>();
    Employee e1 = new Employee("e1",1,10,Gender.MALE, "2019-01-01");
    Employee e2 = new Employee("e2",2,0,Gender.FEMALE, "2019-01-01");
    Employee e3 = new Employee("e3",3,0,Gender.FEMALE, "2019-01-01");

    public EmployeeStore() {
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
    }

    public ArrayList<Employee> getAllEmployees()
   {
       return  employees;
   }
   public Employee getEmployeeById(int id)
   {
       for(Employee empoyee:employees)
       {
           if(empoyee.id == id)
           {
               return empoyee;
           }
       }
       return null;
   }
   public int getLeaveBalance(int id)
   {
    Employee e = getEmployeeById(id);
    LeaveAccrual accrual = new LeaveAccrual();
    accrual.addLeavesMonthly(e,LocalDate.of(2019,6,1));
    return e.getTotal_no_of_leaves()-e.no_of_leaves_taken;
   }
   public boolean isExist(Employee e)
   {
       System.out.println(e);
       return true;
   }

}
