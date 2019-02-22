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
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id){
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
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Employee employee) {
        Employee currentEmployee = employeeService.getEmployeeByID(id);

        if (currentEmployee == null) {return new ResponseEntity("Employee not found",HttpStatus.NOT_FOUND);
        }

        currentEmployee.setName(employee.getName());
        currentEmployee.setGender(employee.getGender());
        currentEmployee.setJoiningDate(employee.getJoiningDate());
        currentEmployee.setNoOfLeavesTaken(employee.getNoOfLeavesTaken());
        currentEmployee.setLeaveTakenFrom(employee.getLeaveTakenFrom());
        currentEmployee.setLeaveTakenTill(employee.getLeaveTakenTill());
        currentEmployee.setMaternityLeaveFrom(employee.getMaternityLeaveFrom());
        currentEmployee.setMaternityLeaveTill(employee.getMaternityLeaveTill());
        currentEmployee.setLeavesLastResetOn(employee.getLeavesLastResetOn());
        currentEmployee.setCompOff(employee.getCompOff());
        currentEmployee.setOptionaLeaves(employee.getOptionaLeaves());
        currentEmployee.setNoOfMaternityLeavesTaken(employee.getNoOfMaternityLeavesTaken());

        employeeService.updateUser(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }
}

