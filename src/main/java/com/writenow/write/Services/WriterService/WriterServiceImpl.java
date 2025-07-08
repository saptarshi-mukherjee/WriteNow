package com.writenow.write.Services.WriterService;

import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.Models.*;
import com.writenow.write.Projections.WriterProjection;
import com.writenow.write.Repositories.CommentRepository;
import com.writenow.write.Repositories.LikeRepository;
import com.writenow.write.Repositories.StoryRepository;
import com.writenow.write.Repositories.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterRepository writerRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StoryRepository storyRepository;

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
    }
}
