package com.writenow.write.Services.WriterService;

import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.Models.EmailStatus;
import com.writenow.write.Models.Writer;
import com.writenow.write.Projections.WriterProjection;
import com.writenow.write.Repositories.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;


@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterRepository writerRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
}
