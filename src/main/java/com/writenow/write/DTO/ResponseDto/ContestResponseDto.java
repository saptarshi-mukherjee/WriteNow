package com.writenow.write.DTO.ResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ContestResponseDto {
    private String description;
    private String expiry;
    private List<PromptResponseDto> prompts;

    public ContestResponseDto() {
        prompts=new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public List<PromptResponseDto> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<PromptResponseDto> prompts) {
        this.prompts = prompts;
    }
}
