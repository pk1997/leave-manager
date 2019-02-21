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
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Employee employee) {
        Employee currentEmployee = employeeService.getEmployeeByID(id);

        if (currentEmployee == null) {
            return new ResponseEntity("Employee not found",
                    HttpStatus.NOT_FOUND);
        }

        currentEmployee.setName(employee.getName());
        currentEmployee.setGender(employee.getGender());
        currentEmployee.setJoining_date(employee.getJoining_date());
        currentEmployee.setNo_of_leaves_taken(employee.no_of_leaves_taken);
        currentEmployee.setFromDate(employee.fromDate);
        currentEmployee.setToDate(employee.toDate);
        currentEmployee.setMaternity_leave_from(employee.maternity_leave_from);
        currentEmployee.setMaternity_leave_till(employee.Maternity_leave_till);
        currentEmployee.setLeavesLastResetOn(employee.leavesLastResetOn);
        currentEmployee.setCompOff(employee.compOff);
        currentEmployee.setOptionaLeaves(employee.optionaLeaves);
        currentEmployee.setNo_of_maternity_leaves_taken(employee.no_of_maternity_leaves_taken);

        employeeService.updateUser(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }
}

