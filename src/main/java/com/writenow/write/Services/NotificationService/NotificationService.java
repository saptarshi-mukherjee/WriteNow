package com.writenow.write.Services.NotificationService;

import com.writenow.write.DTO.ResponseDto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    public void createNotification(Long publisherId, Long interactionId, Long subscriberId);
    public List<NotificationResponseDto> getNotifications(String fullName);

}
