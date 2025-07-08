package com.writenow.write.Services.ComplianceCheckService;

import com.writenow.write.Models.ComplianceStatus;

public interface ComplianceCheckService {
    public ComplianceStatus check(long storyId);
}
