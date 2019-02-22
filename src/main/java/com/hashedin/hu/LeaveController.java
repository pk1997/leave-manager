package com.hashedin.hu;

import com.hashedin.hu.models.LeaveResponse;
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
    public LeaveService getLeaveService() {
        return leaveService;
    }

    public void setLeaveService(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @Autowired
    private LeaveService leaveService;

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/leave/apply",method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody LeaveRequest request, UriComponentsBuilder ucBuilder){
        System.out.println("Applying leave ");
        request.empoyee = employeeService.getEmployeeByID((long) request.getEmpId());
        if(request.empoyee == null)
        {
            return new ResponseEntity("Employee with id:" + request.getEmpId()+ "not found",HttpStatus.NOT_FOUND);
        }
        LeaveResponse response = leaveService.applyLeave(request);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(response.getStatus(), HttpStatus.CREATED);
    }
}
