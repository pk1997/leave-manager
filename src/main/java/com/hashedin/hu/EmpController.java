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
        Employee e2 = new Employee("pavan","2019-01-01",10,Gender.MALE);
        employeeService.addEmployee(e2);
    }
    @RequestMapping("/seeemployee")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployeees();
    }
}

