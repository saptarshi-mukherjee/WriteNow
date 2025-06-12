package com.writenow.write.DTO.ResponseDto;

public class PromptResponseDto {
    private long promptId;
    private String prompt;


    public long getPromptId() {
        return promptId;
    }

    public void setPromptId(long promptId) {
        this.promptId = promptId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
