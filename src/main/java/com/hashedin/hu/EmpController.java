package com.hashedin.hu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class EmpController {
    @Autowired
    private EmployeeService employeeService;
    @RequestMapping("/add2")
    public void add2() {
        Employee e2 = new Employee("suman","2019-01-01",10,"MALE");
        employeeService.addEmployee(e2);
    }
    @RequestMapping("/employee")
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
    @RequestMapping(value = "/employees/",method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder){
        System.out.println("Creating User "+employee.getName());
        employeeService.addEmployee(employee);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employees/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
