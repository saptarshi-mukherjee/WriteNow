package com.writenow.write.Services.ContestService;


import com.writenow.write.DTO.ResponseDto.ContestResponseDto;
import com.writenow.write.DTO.ResponseDto.PromptResponseDto;
import com.writenow.write.Models.Contest;
import com.writenow.write.Models.Moderator;
import com.writenow.write.Models.Prompt;
import com.writenow.write.Repositories.ContestRepository;
import com.writenow.write.Repositories.ModeratorRepository;
import com.writenow.write.Repositories.PromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ModeratorRepository moderatorRepository;
    @Autowired
    private PromptRepository promptRepository;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


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
}
