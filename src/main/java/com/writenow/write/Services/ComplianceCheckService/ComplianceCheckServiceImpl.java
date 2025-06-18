package com.writenow.write.Services.ComplianceCheckService;

import com.writenow.write.Models.ComplianceStatus;
import com.writenow.write.Models.Story;
import com.writenow.write.Repositories.StoryRepository;
import com.writenow.write.Services.AIService.AIService;
import com.writenow.write.Services.WinstonService.WinstonService;
import com.writenow.write.Strategies.ComplianceStrategy.FourFoldStrategy;
import com.writenow.write.Strategies.ComplianceStrategy.StoryComplianceStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplianceCheckServiceImpl implements ComplianceCheckService {

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private AIService aiService;
    @Autowired
    private WinstonService winstonService;


    @Override
    @Transactional
    public void check(long storyId) {

        Story story=storyRepository.fetchStoryById(storyId);
        StoryComplianceStrategy strategy=new FourFoldStrategy(aiService,winstonService);
        if(strategy.check(story.getPrompt().getDescription(), story.getBody()))
            story.setComplianceStatus(ComplianceStatus.COMPLIANT);
        else
            story.setComplianceStatus(ComplianceStatus.NOT_COMPLIANT);
        storyRepository.save(story);

    }
}
