package com.writenow.write.Controllers.WriterController;


import com.writenow.write.DTO.RequestDto.CommentRequestDto;
import com.writenow.write.DTO.RequestDto.WriterRequestDto;
import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.Services.WriterService.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/writer")
public class WriterController {


    @Autowired
    private WriterService writerService;


    @PostMapping("/post")
    public String addWriter(@RequestBody WriterRequestDto request) {
        return writerService.addWriter(request.getFullName(), request.getEmail());
    }

    @GetMapping("/get/{fullName}")
    public WriterResponseDto getWriter(@PathVariable("fullName") String fullName) {
        return writerService.getWriterByName(fullName);
    }


    @PostMapping("/like")
    public void likeStory(@RequestParam("name") String fullName, @RequestParam("storyId") long storyId) {
        writerService.likeStory(fullName, storyId);
    }


    @PostMapping("/comment")
    public void comment(@RequestBody CommentRequestDto request) {
        writerService.addComment(request.getFullName(), request.getStoryId(), request.getCommentText());
    }

}
