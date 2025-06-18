package com.writenow.write.Services.WinstonService;

import com.writenow.write.DTO.APIResposeDto.AIDetectionDto;
import com.writenow.write.DTO.APIResposeDto.PlagResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WinstonServiceImpl implements WinstonService {
    @Override
    public double checkPlagiarism(String story) {
        String url="https://api.gowinston.ai/v2/plagiarism";
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization", "Bearer 5QaO8QJhIFsxllap94hK078S7fNvqNGDlEs7ggL80e9eb6fa");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        Map<String,String> body=new HashMap<>();
        body.put("text",story);
        body.put("language","en");
        body.put("country","in");
        HttpEntity<Map<String,String>> entity=new HttpEntity<>(body,headers);
        ResponseEntity<PlagResponseDto> response=restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<PlagResponseDto>(){}
        );
        return response.getBody().getResult().getScore();
    }

    @Override
    public double checkAI(String story) {
        String url="https://api.gowinston.ai/v2/ai-content-detection";
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization", "Bearer 5QaO8QJhIFsxllap94hK078S7fNvqNGDlEs7ggL80e9eb6fa");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        //headers.add("Content-Type", "application/json");
        Map<String,String> body=new HashMap<>();
        body.put("text",story);
        body.put("version","latest");
        body.put("language","en");
        HttpEntity<Map<String,String>> entity=new HttpEntity<>(body,headers);
        ResponseEntity<AIDetectionDto> response=restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<AIDetectionDto>(){}
        );
        return response.getBody().getScore();
    }
}
