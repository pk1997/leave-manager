package com.hashedin.hu;

import com.hashedin.hu.models.CompOff;
import com.hashedin.hu.models.LogHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private LeaveAccrual leaveAccrual = new LeaveAccrual();
    private CompOff compOff = new CompOff();

    public void addEmployee(Employee e)
    {
        employeeRepository.save(e);
    }
    public List<Employee> getAllEmployeees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }
    public Employee getEmployeeByID(Long id){
        if(employeeRepository.existsById(id)) {
            return employeeRepository.findById(id).get();
        }
        return null;
    }

    public Employee updateUser(Employee currentEmployee) {
        Employee currentEmployee1 = currentEmployee;
        employeeRepository.save(currentEmployee1);
        return currentEmployee1;
    }

    public int getNoOfLeavesAvailable(long id) {
        Employee employee = getEmployeeByID(id);
        leaveAccrual.addLeavesMonthly(employee, LocalDate.now());
        int i = employee.getLeavesCarriedFromLastYear() + employee.getTotalNoOfLeaves() - employee.getNoOfLeavesTaken();
        return i;
    }


    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void logHours(Employee employee, LogHours hours) {
        employee.getCompOff().setWorkedOn(hours.getFrom(),hours.getTo());
        employeeRepository.save(employee);
        return;
    }
}

