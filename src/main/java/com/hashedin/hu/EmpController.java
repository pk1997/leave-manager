package com.hashedin.hu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class EmpController {
    @Autowired
    private EmployeeService employeeService;
    @RequestMapping("/employees")
    public void allEmployees() {
        Employee e = new Employee("pavan", (long) 1, 10, Gender.MALE, "2019-01-01");
        employeeService.addEmployee(e);
    }
    @RequestMapping("/seeemployee")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployeees();
    }
}

