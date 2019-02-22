package com.hashedin.hu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    public LeaveRepositiory getRepositiory() {
        return repositiory;
    }

    public void setRepositiory(LeaveRepositiory repositiory) {
        this.repositiory = repositiory;
    }

    public LeaveManager getManager() {
        return manager;
    }

    public void setManager(LeaveManager manager) {
        this.manager = manager;
    }

    @Autowired
    private LeaveRepositiory repositiory;
    @Autowired
    private LeaveManager manager;

    public LeaveResponse applyLeave(LeaveRequest request)
    {
        LeaveResponse response = manager.applyLeave(request);
        return response;
    }
}
