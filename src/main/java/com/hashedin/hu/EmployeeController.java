package com.hashedin.hu;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class EmployeeController {
    EmployeeStore store;
    public EmployeeController(EmployeeStore store) {
        this.store = store;
    }

    @RequestMapping("/employees")
    public ArrayList<Employee> allEmployees(){
        return store.getAllEmployees();
    }
    @RequestMapping("/employees/{id}")
    private Employee getEmployeeById(@PathVariable("id") int id)
    {
        return store.getEmployeeById(id);
    }
    @RequestMapping("/employees/{id}/availableLeaves")
    private int getAvailableLeaves(@PathVariable("id") int id)
    {
        return store.getLeaveBalance(id);
    }
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public Employee createUser(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        if (store.isExist(employee)) {

        }
        return employee;
    }


}
