package com.writenow.write.Strategies.ComplianceStrategy;

import com.writenow.write.Services.AIService.AIService;
import com.writenow.write.Services.WinstonService.WinstonService;

public class FourFoldStrategy implements StoryComplianceStrategy {

    private AIService aiService;
    private WinstonService winstonService;

    public FourFoldStrategy(AIService aiService, WinstonService winstonService) {
        this.aiService = aiService;
        this.winstonService = winstonService;
    }

    @Override
    public boolean check(String prompt, String story) {
        return (checkWordCount(story) && checkPlagiarism(story) && checkAI(story) && checkPromptAdherence(prompt, story));
    }


    private boolean checkWordCount(String s) {
        int n=s.length(), i, start=-1, end=-1, spaceCount=0;
        for(i=0;i<n;i++) {
            if(s.charAt(i)!=' ') {
                start=i;
                break;
            }
        }
        for(i=n-1;i>=0;i--) {
            if(s.charAt(i)!=' ') {
                end=i;
                break;
            }
        }
        for(i=start;i<=end;i++) {
            if(s.charAt(i)==' ' && s.charAt(i-1)==' ')
                continue;
            else if (s.charAt(i)==' ') {
                spaceCount++;
            }
        }
        int wordCount=spaceCount+1;
        return (wordCount>=150 && wordCount<=10000);
    }


    private boolean checkPlagiarism(String story) {
        double score= winstonService.checkPlagiarism(story);
        return (score<=25);
    }


    private boolean checkAI(String story) {
        double score= winstonService.checkAI(story);
        return (score>=75);
    }


    private boolean checkPromptAdherence(String prompt, String story) {
        String response=aiService.checkPromptAdherence(prompt, story);
        return response.equalsIgnoreCase("yes");
    }
}
