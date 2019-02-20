package com.hashedin.hu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeeStore {
    @Autowired
   private EmployeeRepository employeeRepository;
   private ArrayList<Employee> employees = new ArrayList<>();
    Employee e1 = new Employee("e1", (long) 1,10,Gender.MALE, "2019-01-01");
    Employee e2 = new Employee("e2", (long) 2,0,Gender.FEMALE, "2019-01-01");
    Employee e3 = new Employee("e3", (long) 3,0,Gender.FEMALE, "2019-01-01");

    public EmployeeStore(EmployeeRepository employeeRepository) {
        employeeRepository.save(e1);
    }

    public void AddEmployee(Employee e)
   {
       employeeRepository.save(e);
   }
   public List<Employee> getAllEmployees()
    {   List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
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
