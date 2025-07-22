package com.writenow.write.DTO.ResponseDto;

import java.util.ArrayList;
import java.util.List;

public class WriterToStoryResponseDto {
    private List<StoryListResponseDto> storyList;


    public WriterToStoryResponseDto() {
        storyList=new ArrayList<>();
    }


    public List<StoryListResponseDto> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<StoryListResponseDto> storyList) {
        this.storyList = storyList;
    }
}
