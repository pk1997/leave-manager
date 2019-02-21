package com.hashedin.hu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

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
}

