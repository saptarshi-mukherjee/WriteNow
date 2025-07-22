package com.writenow.write.Services.WriterService;

import com.writenow.write.DTO.ResponseDto.FollowerResponseDto;
import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.DTO.ResponseDto.WriterToStoryResponseDto;

import java.util.List;

public interface WriterService {
    public String addWriter(String fullName, String email);
    public WriterResponseDto getWriterByName(String fullName);
    public void likeStory(String fullName, long storyId);
    public void addComment(String fullName, long storyId, String commentText);
    public void follow(long followerId, long followingId);
    public List<FollowerResponseDto> getFollowers(long writerId);
    public WriterToStoryResponseDto getStoryByWriter(String fullName);
}
