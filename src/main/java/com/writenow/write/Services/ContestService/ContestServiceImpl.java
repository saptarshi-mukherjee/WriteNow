package com.writenow.write.Services.ContestService;


import com.writenow.write.DTO.ResponseDto.CommentResponseDto;
import com.writenow.write.DTO.ResponseDto.ContestResponseDto;
import com.writenow.write.DTO.ResponseDto.PromptResponseDto;
import com.writenow.write.DTO.ResponseDto.StoryResponseDto;
import com.writenow.write.Models.*;
import com.writenow.write.Projections.UserIdProjection;
import com.writenow.write.Repositories.*;
import com.writenow.write.Services.ComplianceCheckService.ComplianceCheckService;
import com.writenow.write.Threads.ComplianceCheckThread;
import jakarta.annotation.PreDestroy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ContestServiceImpl implements ContestService {

    private ContestRepository contestRepository;
    private ModeratorRepository moderatorRepository;
    private PromptRepository promptRepository;
    private RedisTemplate<String,Object> redisTemplate;
    private WriterRepository writerRepository;
    private StoryRepository storyRepository;
    private UserRepository userRepository;
    private ExecutorService exs;
    private ComplianceCheckService complianceCheckService;
    public static final Object lock=new Object();
    private JavaMailSender mailSender;

    public ContestServiceImpl(ContestRepository contestRepository, ModeratorRepository moderatorRepository, PromptRepository promptRepository,
                              RedisTemplate<String, Object> redisTemplate, WriterRepository writerRepository, StoryRepository storyRepository,
                              UserRepository userRepository, ComplianceCheckService complianceCheckService, JavaMailSender mailSender) {
        this.contestRepository = contestRepository;
        this.moderatorRepository = moderatorRepository;
        this.promptRepository = promptRepository;
        this.redisTemplate = redisTemplate;
        this.writerRepository = writerRepository;
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.exs = Executors.newFixedThreadPool(10);
        this.complianceCheckService=complianceCheckService;
        this.mailSender=mailSender;
    }

    @Override
    public String createContest(String description, long modId, List<String> prompts) {
        Contest contest=new Contest();
        contest.setDescription(description);
        Moderator moderator=moderatorRepository.findById(modId);
        contest.setCreatedBy(moderator);
        contestRepository.save(contest);
        for(String promptText : prompts) {
            Prompt prompt=new Prompt();
            prompt.setDescription(promptText);
            prompt.setContest(contest);
            promptRepository.save(prompt);
            contest.getPrompts().add(prompt);
        }

        contestRepository.save(contest);
        return "new contest created";
    }

    @Override
    public ContestResponseDto getContest(long id) {
        ContestResponseDto cachedResponse=(ContestResponseDto)redisTemplate.opsForValue().get("CONTEST::"+id);
        if(cachedResponse!=null)
            return cachedResponse;
        Contest contest=contestRepository.fetchContestById(id);
        List<PromptResponseDto> promptResponseList=new ArrayList<>();
        ContestResponseDto response=new ContestResponseDto();
        response.setDescription(contest.getDescription());
        response.setExpiry(contest.getExpiry().toString());
        for(Prompt prompt : contest.getPrompts()) {
            PromptResponseDto promptResponse=new PromptResponseDto();
            promptResponse.setPromptId(prompt.getId());
            promptResponse.setPrompt(prompt.getDescription());
            promptResponseList.add(promptResponse);
        }
        response.setPrompts(promptResponseList);
        redisTemplate.opsForValue().set("CONTEST::"+id, response, Duration.ofMinutes(120));
        return response;
    }

    @Override
    public String submitStory(String fullName, long promptId, String title, String body) {
        List<UserIdProjection> userIdProjectionList=userRepository.fetchByName(fullName);
        long userId=userIdProjectionList.getFirst().getId();
        Optional<Writer> optionalWriter=writerRepository.findById(userId);
        Writer writer=null;
        if(optionalWriter.isPresent())
            writer=optionalWriter.get();
        Optional<Prompt> optionalPrompt=promptRepository.findById(promptId);
        Prompt prompt=null;
        if(optionalPrompt.isPresent())
            prompt=optionalPrompt.get();
        Story story=new Story();
        story.setWriter(writer);
        story.setPrompt(prompt);
        story.setStoryStatus(StoryStatus.PARTICIPANT);
        story.setTitle(title);
        story.setBody(body);
        story=storyRepository.save(story);
        ComplianceCheckThread thread=new ComplianceCheckThread(complianceCheckService,lock,story.getId(), mailSender , writer.getEmail());
        exs.submit(thread);
        return "story submitted successfully";
    }

    @Override
    public StoryResponseDto getStory(long storyId) {
        StoryResponseDto cachedResponse=(StoryResponseDto) redisTemplate.opsForValue().get("STORY::"+storyId);
        if(cachedResponse!=null)
            return cachedResponse;
        Story story=storyRepository.fetchStoryById(storyId);
        StoryResponseDto response=new StoryResponseDto();
        response.setTitle(story.getTitle());
        response.setWriter(story.getWriter().getFullName());
        response.setPrompt(story.getPrompt().getDescription());
        response.setText(story.getBody());
        response.setLikeCount(story.getLikes().size());
        for(Comment comment : story.getComments()) {
            CommentResponseDto commentResponse=new CommentResponseDto();
            commentResponse.setCommenter(comment.getWriter().getFullName());
            commentResponse.setCommentTime(comment.getCreatedAt().toString());
            commentResponse.setCommentText(comment.getCommentText());
            response.getComments().add(commentResponse);
        }
        redisTemplate.opsForValue().set("STORY::"+storyId, response, Duration.ofMinutes(10));
        return response;
    }


    @PreDestroy
    public void shutdown() {
        exs.shutdown();
    }
}
