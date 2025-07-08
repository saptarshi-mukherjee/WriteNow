package com.writenow.write.Services.ContestService;

import com.writenow.write.DTO.ResponseDto.ContestResponseDto;
import com.writenow.write.DTO.ResponseDto.StoryResponseDto;

import java.util.List;

public interface ContestService {
    public String createContest(String description, long modId, List<String> prompts);
    public ContestResponseDto getContest(long id);
    public String submitStory(String fullName, long promptId, String title, String body);
    public StoryResponseDto getStory(long storyId);
}
