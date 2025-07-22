package com.writenow.write.Services.WriterService;

import com.writenow.write.DTO.ResponseDto.FollowerResponseDto;
import com.writenow.write.DTO.ResponseDto.StoryListResponseDto;
import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.DTO.ResponseDto.WriterToStoryResponseDto;
import com.writenow.write.Models.*;
import com.writenow.write.Projections.WriterProjection;
import com.writenow.write.Repositories.CommentRepository;
import com.writenow.write.Repositories.LikeRepository;
import com.writenow.write.Repositories.StoryRepository;
import com.writenow.write.Repositories.WriterRepository;
import com.writenow.write.Services.NotificationService.NotificationService;
import com.writenow.write.Threads.NotificationCreationThread;
import jakarta.annotation.PreDestroy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class WriterServiceImpl implements WriterService {

    private WriterRepository writerRepository;
    private RedisTemplate<String, Object> redisTemplate;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private StoryRepository storyRepository;
    private NotificationService notificationService;
    private ExecutorService exs;
    public static final Object lock=new Object();

    public WriterServiceImpl(WriterRepository writerRepository, RedisTemplate<String, Object> redisTemplate, LikeRepository likeRepository,
                             CommentRepository commentRepository, StoryRepository storyRepository, NotificationService notificationService) {
        this.writerRepository = writerRepository;
        this.redisTemplate = redisTemplate;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.storyRepository = storyRepository;
        this.notificationService = notificationService;
        //this.exs = Executors.newFixedThreadPool(20);
    }

    @Override
    public String addWriter(String fullName, String email) {
        Writer writer=new Writer();
        writer.setFullName(fullName);
        writer.setEmail(email);
        writerRepository.save(writer);
        return "new writer added";
    }

    @Override
    public WriterResponseDto getWriterByName(String fullName) {
        WriterResponseDto cachedResponse=(WriterResponseDto) redisTemplate.opsForValue().get("WRITER::"+fullName);
        if(cachedResponse!=null) {
            System.out.println("FROM CACHE!!!");
            return cachedResponse;
        }
        List<WriterProjection> writerProjections=writerRepository.fetchWriterByName(fullName);
        WriterResponseDto response=new WriterResponseDto();
        response.setFulName(writerProjections.getFirst().getFull_name());
        response.setEmail(writerProjections.getFirst().getEmail());
        response.setId(writerProjections.getFirst().getId());
        response.setUrl(writerProjections.getFirst().getUrl());
        response.setEmailStatus(EmailStatus.values()[writerProjections.getFirst().getStatus()].toString());
        redisTemplate.opsForValue().set("WRITER::"+fullName, response, Duration.ofMinutes(30));
        return response;
    }

    @Override
    public void likeStory(String fullName, long storyId) {
        List<WriterProjection> writerProjections=writerRepository.fetchWriterByName(fullName);
        Optional<Writer> writerOptional=writerRepository.findById(writerProjections.getFirst().getId());
        Writer writer=null;
        if(writerOptional.isPresent())
            writer=writerOptional.get();
        Story story=storyRepository.fetchStoryById(storyId);
        Like like=new Like();
        like.setWriter(writer);
        like.setStory(story);
        like=likeRepository.save(like);
        story.getLikes().add(like);
        storyRepository.save(story);
        writer.getLikes().add(like);
        writerRepository.save(writer);
        notificationService.createNotification(writer.getId(), like.getId(), story.getWriter().getId());
    }

    @Override
    public void addComment(String fullName, long storyId, String commentText) {
        List<WriterProjection> writerProjections=writerRepository.fetchWriterByName(fullName);
        Optional<Writer> writerOptional=writerRepository.findById(writerProjections.getFirst().getId());
        Writer writer=null;
        if(writerOptional.isPresent())
            writer=writerOptional.get();
        Story story=storyRepository.fetchStoryById(storyId);
        Comment comment=new Comment();
        comment.setWriter(writer);
        comment.setStory(story);
        comment.setCommentText(commentText);
        comment=commentRepository.save(comment);
        story.getComments().add(comment);
        storyRepository.save(story);
        writer.getComments().add(comment);
        writerRepository.save(writer);
        notificationService.createNotification(writer.getId(), comment.getId(), story.getWriter().getId());
    }

    @Override
    public void follow(long followerId, long followingId) {
        Writer follower=writerRepository.findById(followerId).get();
        Writer following=writerRepository.findById(followingId).get();
        following.getFollowers().add(follower);
        writerRepository.save(following);
        follower.getFollowing().add(following);
        writerRepository.save(follower);
        notificationService.createNotification(follower.getId(),null, following.getId());
    }

    @Override
    public List<FollowerResponseDto> getFollowers(long writerId) {
        List<FollowerResponseDto> cachedList=(List<FollowerResponseDto>) redisTemplate.opsForValue().get("FOLLOWERS::"+writerId);
        if(cachedList!=null)
            return cachedList;
        Writer writer=writerRepository.findById(writerId).get();
        List<Writer> followers=writer.getFollowers();
        List<FollowerResponseDto> responseList=new ArrayList<>();
        for(Writer follower : followers) {
            FollowerResponseDto response=new FollowerResponseDto();
            response.setFollowerId(follower.getId());
            response.setFollowerName(follower.getFullName());
            responseList.add(response);
        }
        redisTemplate.opsForValue().set("FOLLOWERS::"+writerId, responseList, Duration.ofMinutes(20));
        return responseList;
    }

    @Override
    public WriterToStoryResponseDto getStoryByWriter(String fullName) {
        List<WriterProjection> writerProjectionList=writerRepository.fetchWriterByName(fullName);
        long writerId=writerProjectionList.getFirst().getId();
        WriterToStoryResponseDto cachedResponse= (WriterToStoryResponseDto) redisTemplate.opsForValue().get("STORY-WRITER::"+writerId);
        if(cachedResponse!=null)
            return cachedResponse;
        List<Story> stories=storyRepository.fetchStoriesByWriterId(writerId);
        WriterToStoryResponseDto response=new WriterToStoryResponseDto();
        for(Story story : stories) {
            StoryListResponseDto storyResponse=new StoryListResponseDto();
            storyResponse.setStoryId(story.getId());
            storyResponse.setTitle(story.getTitle());
            storyResponse.setSubmissionDate(story.getSubmissionDate().toString());
            storyResponse.setPromptId(story.getPrompt().getId());
            storyResponse.setPrompt(story.getPrompt().getDescription());
            response.getStoryList().add(storyResponse);
        }
        redisTemplate.opsForValue().set("STORY-WRITER::"+writerId, response, Duration.ofMinutes(60));
        return response;
    }


//    @PreDestroy
//    public void shutdown() {
//        exs.shutdown();
//    }
}
