package com.writenow.write.DTO.RequestDto;

import java.util.List;

public class ContestRequestDto {
    private String description;
    private long modId;
    private List<String> prompts;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getModId() {
        return modId;
    }

    public void setModId(long modId) {
        this.modId = modId;
    }

    public List<String> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<String> prompts) {
        this.prompts = prompts;
    }
}
