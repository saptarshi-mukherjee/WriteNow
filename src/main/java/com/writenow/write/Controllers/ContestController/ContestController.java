package com.writenow.write.Controllers.ContestController;


import com.writenow.write.DTO.RequestDto.ContestRequestDto;
import com.writenow.write.DTO.RequestDto.StoryRequestDto;
import com.writenow.write.DTO.ResponseDto.ContestResponseDto;
import com.writenow.write.Services.ContestService.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;


    @PostMapping("/post")
    public String createContest(@RequestBody ContestRequestDto request) {
        return contestService.createContest(request.getDescription(), request.getModId(), request.getPrompts());
    }


    @GetMapping("/get/{id}")
    public ContestResponseDto getContest(@PathVariable("id") long id) {
        return contestService.getContest(id);
    }


    @PostMapping("/submit")
    public String submitStory(@RequestBody StoryRequestDto request) {
        return contestService.submitStory(request.getFullName(), request.getPromptId(), request.getTitle(), request.getBody());
    }
}
