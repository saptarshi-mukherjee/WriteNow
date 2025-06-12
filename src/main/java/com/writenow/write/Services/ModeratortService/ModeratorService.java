package com.writenow.write.Services.ModeratortService;

import com.writenow.write.DTO.ResponseDto.ModeratorResponseDto;

public interface ModeratorService {
    public String addMod(String fullName, String email, String moderatorStatus);
    public ModeratorResponseDto getModByName(String fullName);
}
