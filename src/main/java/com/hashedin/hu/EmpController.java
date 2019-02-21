package com.hashedin.hu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping("/employee/{id}")
    public ResponseEntity<Employee> getUserById(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployeeByID((long) id);
        if(employee == null)
        {
            return new ResponseEntity("Employee with id" + id +"not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
}

