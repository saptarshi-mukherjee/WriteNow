package com.writenow.write.Services.ModeratortService;

import com.writenow.write.DTO.ResponseDto.ModeratorResponseDto;
import com.writenow.write.Models.EmailStatus;
import com.writenow.write.Models.Moderator;
import com.writenow.write.Models.ModeratorStatus;
import com.writenow.write.Projections.ModeratorProjection;
import com.writenow.write.Repositories.ModeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;


@Service
public class ModeratorServiceImpl implements ModeratorService {

    @Autowired
    private ModeratorRepository moderatorRepository;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public String addMod(String fullName, String email, String moderatorStatus) {
        Moderator moderator=new Moderator();
        moderator.setFullName(fullName);
        moderator.setEmail(email);
        moderator.setModeratorStatus(ModeratorStatus.valueOf(moderatorStatus.toUpperCase()));
        moderatorRepository.save(moderator);
        return "new moderator added";
    }


    public ModeratorResponseDto getModByName(String fullName) {
        ModeratorResponseDto cachedResponse=(ModeratorResponseDto) redisTemplate.opsForValue().get("MODERATOR::"+fullName);
        if(cachedResponse!=null)
            return cachedResponse;
        List<ModeratorProjection> moderatorProjections=moderatorRepository.fetchModeratorByName(fullName);
        ModeratorResponseDto response=new ModeratorResponseDto();
        response.setId(moderatorProjections.getFirst().getId());
        response.setFullName(moderatorProjections.getFirst().getFull_name());
        response.setContestCount(moderatorProjections.getFirst().getContest_count());
        response.setEmail(moderatorProjections.getFirst().getEmail());
        response.setEmailStatus(EmailStatus.values()[moderatorProjections.getFirst().getStatus()].toString());
        response.setModeratorStatus(ModeratorStatus.values()[moderatorProjections.getFirst().getModerator_status()].toString());
        redisTemplate.opsForValue().set("MODERATOR::"+fullName, response, Duration.ofMinutes(30));
        return response;
    }
}
