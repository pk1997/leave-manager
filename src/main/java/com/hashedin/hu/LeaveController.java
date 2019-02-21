package com.hashedin.hu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class LeaveController {
    @Autowired
    LeaveService leaveService;
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/leave/apply",method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody LeaveRequest request, UriComponentsBuilder ucBuilder){
        System.out.println("Applying leave ");
        request.empoyee = employeeService.getEmployeeByID((long) request.getEmp_id());
        if(request.empoyee == null)
        {
            return new ResponseEntity("Employee with id:" + request.getEmp_id()+ "not found",HttpStatus.NOT_FOUND);
        }
        LeaveResponse response = leaveService.applyLeave(request);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(response.status, HttpStatus.CREATED);
    }
}