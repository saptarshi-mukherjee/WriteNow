package com.writenow.write.DTO.APIResposeDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlagResponseDto {
    private PlagResult result;

    public PlagResult getResult() {
        return result;
    }

    public void setResult(PlagResult result) {
        this.result = result;
    }
}
