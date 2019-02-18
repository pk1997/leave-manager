package com.hashedin.hu;

import java.util.HashMap;
import java.util.Map;

public class EmployeeStore {
    public  Map<Integer,Empoyee> employee = new HashMap<>();

    public Empoyee getEmployee(int id) {
        if(employee.containsKey(id)) {
            return employee.get(id);
        }
        else System.out.println("not found");
        return null;
    }

    public void setEmployee(int id, Empoyee e) {
        employee.put(id,e);
    }
}
