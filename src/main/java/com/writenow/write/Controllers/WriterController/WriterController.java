package com.writenow.write.Controllers.WriterController;


import com.writenow.write.DTO.RequestDto.CommentRequestDto;
import com.writenow.write.DTO.RequestDto.FollowerRequestDto;
import com.writenow.write.DTO.RequestDto.WriterRequestDto;
import com.writenow.write.DTO.ResponseDto.FollowerResponseDto;
import com.writenow.write.DTO.ResponseDto.NotificationResponseDto;
import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.DTO.ResponseDto.WriterToStoryResponseDto;
import com.writenow.write.Services.NotificationService.NotificationService;
import com.writenow.write.Services.WriterService.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writer")
public class WriterController {


    @Autowired
    private WriterService writerService;
    @Autowired
    private NotificationService notificationService;


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

    @GetMapping("/notifications/{fullName}")
    public List<NotificationResponseDto> getNotifications(@PathVariable("fullName") String fullName) {
        return notificationService.getNotifications(fullName);
    }


    @PostMapping("/follow")
    public void follow(@RequestBody FollowerRequestDto request) {
        writerService.follow(request.getFollowerId(), request.getFollowingId());
    }


    @GetMapping("/get/followers/{writerId}")
    public List<FollowerResponseDto> getFollowers(@PathVariable("writerId") long writerId) {
        return writerService.getFollowers(writerId);
    }


    @GetMapping("/get/stories/{fullName}")
    public WriterToStoryResponseDto getStoriesByWriter(@PathVariable("fullName") String fullName) {
        return writerService.getStoryByWriter(fullName);
    }

}
