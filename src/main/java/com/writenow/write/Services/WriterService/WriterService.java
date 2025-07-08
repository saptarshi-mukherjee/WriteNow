package com.writenow.write.Services.WriterService;

import com.writenow.write.DTO.ResponseDto.WriterResponseDto;

public interface WriterService {
    public String addWriter(String fullName, String email);
    public WriterResponseDto getWriterByName(String fullName);
    public void likeStory(String fullName, long storyId);
    public void addComment(String fullName, long storyId, String commentText);
}
