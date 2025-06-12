package com.writenow.write.Controllers.ModeratorController;


import com.writenow.write.DTO.RequestDto.ModeratorRequestDto;
import com.writenow.write.DTO.ResponseDto.ModeratorResponseDto;
import com.writenow.write.Services.ModeratortService.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mod")
public class ModeratorController {

    @Autowired
    private ModeratorService moderatorService;


    @PostMapping("/post")
    public String addMod(@RequestBody ModeratorRequestDto request) {
        return moderatorService.addMod(request.getFullName(), request.getEmail(), request.getModeratorStatus());
    }

    @GetMapping("/get/{fullName}")
    public ModeratorResponseDto getMod(@PathVariable("fullName") String fullName) {
        return moderatorService.getModByName(fullName);
    }
}
