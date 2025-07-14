package com.writenow.write.Services.NotificationService;


import com.writenow.write.DTO.ResponseDto.NotificationResponseDto;
import com.writenow.write.Models.*;
import com.writenow.write.Projections.WriterProjection;
import com.writenow.write.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private WriterRepository writerRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    @Transactional
    @Async
    public void createNotification(Long publisherId, Long interactionId, Long subscriberId) {
        Writer publisher=null, subscriber=null;
        Interaction interaction=null;
        Optional<Interaction> interactionOptional=null;
        Optional<Writer> writerOptional=writerRepository.findById(publisherId);
        if(writerOptional.isPresent())
            publisher=writerOptional.get();
        Optional<Writer> subOptional=writerRepository.findById(subscriberId);
        if(subOptional.isPresent())
            subscriber=subOptional.get();
        if(interactionId!=null) {
            interactionOptional = interactionRepository.findById(interactionId);
            if (interactionOptional.isPresent())
                interaction = interactionOptional.get();
        }
        Notification notification=new Notification();
        notification.setPublisher(publisher);
        notification.setSubscriber(subscriber);
        notification.setInteraction(interaction);
        if(interaction instanceof Like)
            notification.setType(NotificationType.LIKE);
        else if(interaction instanceof Comment)
            notification.setType(NotificationType.COMMENT);
        else if(interaction==null)
            notification.setType(NotificationType.FOLLOW);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponseDto> getNotifications(String fullName) {
        List<WriterProjection> writerProjectionList=writerRepository.fetchWriterByName(fullName);
        Writer subscriber=null;
        Optional<Writer> writerOptional =writerRepository.findById(writerProjectionList.getFirst().getId());
        if(writerOptional.isPresent())
            subscriber=writerOptional.get();
        List<NotificationResponseDto> cachedList=(List<NotificationResponseDto>) redisTemplate.opsForValue().get("SUBSCRIBER::"+subscriber.getId());
        if(cachedList!=null) {
            System.out.println("FROM CACHE!!!");
            return cachedList;
        }
        List<Notification> notificationList=notificationRepository.fetchById(subscriber.getId());
        List<NotificationResponseDto> responseList=new ArrayList<>();
        for(Notification notification : notificationList) {
            NotificationResponseDto response=new NotificationResponseDto();
            response.setCreatedAt(notification.getCreatedAt().toString());
            response.setNotificationType(notification.getType().toString());
            response.setPublisherId(notification.getPublisher().getId());
            response.setPublisherName(notification.getPublisher().getFullName());
            if(notification.getType()==NotificationType.LIKE) {
                Story story=likeRepository.findById(notification.getInteraction().getId()).get().getStory();
                response.setStoryId(story.getId());
            }
            else if (notification.getType()==NotificationType.COMMENT) {
                Story story=commentRepository.findById(notification.getInteraction().getId()).get().getStory();
                response.setStoryId(story.getId());
            }
            else
                response.setStoryId(-1);
            responseList.add(response);
        }
        redisTemplate.opsForValue().set("SUBSCRIBER::"+subscriber.getId(), responseList, Duration.ofMinutes(5));
        return responseList;

    }
}
