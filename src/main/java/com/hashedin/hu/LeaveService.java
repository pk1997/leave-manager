package com.hashedin.hu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    @Autowired
    LeaveRepositiory repositiory;
    @Autowired
    LeaveManager manager;

    public LeaveResponse applyLeave(LeaveRequest request)
    {
        LeaveResponse response = manager.ApplyLeave(request);
        return response;
    }
}
