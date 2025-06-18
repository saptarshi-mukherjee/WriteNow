package com.writenow.write.Services.AIService;


import com.writenow.write.DTO.APIResposeDto.OpenAiResponseDto;
import org.springframework.ai.chat.ChatClient;
//import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIServiceImpl implements AIService {


    @Override
    public String checkPromptAdherence(String prompt, String story) {
        String url="https://api.openai.com/v1/chat/completions";
        String apiKey="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";
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization", "Bearer "+apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        Map<String,String> systemMap=new HashMap<>();
        systemMap.put("role","system");
        systemMap.put("content", "You are a judge in a creative writing contest. Check if the story adheres meaningfully to the given prompt.\n Reply only yes or no. " +
                "Explanation not needed.");
        Map<String,String> userMap=new HashMap<>();
        userMap.put("role","user");
        userMap.put("content", "Prompt : "+prompt+"\n Story :  "+story);
        Map<String,Object> request=new HashMap<>();
        request.put("model","gpt-4o");
        request.put("messages", List.of(systemMap,userMap));
        HttpEntity<Map<String,Object>> requestEntity=new HttpEntity<>(request,headers);
        ResponseEntity<OpenAiResponseDto> response=restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<OpenAiResponseDto>() {}
        );
        return response.getBody().getChoices().getFirst().getMessage().getContent();

    }
}
