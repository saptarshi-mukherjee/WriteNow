package com.writenow.write.Threads;

import com.writenow.write.Services.ComplianceCheckService.ComplianceCheckService;

public class ComplianceCheckThread implements Runnable {

    private ComplianceCheckService complianceCheckService;
    private Object lock;
    private Long storyId;

    public ComplianceCheckThread(ComplianceCheckService complianceCheckService, Object lock, Long storyId) {
        this.complianceCheckService = complianceCheckService;
        this.lock = lock;
        this.storyId=storyId;
    }

    @Override
    public void run() {
        synchronized (lock) {
            complianceCheckService.check(storyId);
        }
    }
}
