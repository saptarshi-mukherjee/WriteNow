package com.writenow.write.Threads;

import com.writenow.write.Services.NotificationService.NotificationService;

public class NotificationCreationThread implements Runnable {

    private NotificationService notificationService;
    public Object lock;
    private Long publisherId, subscriberId, interactionId;

    public NotificationCreationThread(NotificationService notificationService, Object lock, Long publisherId, Long subscriberId, Long interactionId) {
        this.notificationService = notificationService;
        this.lock = lock;
        this.publisherId = publisherId;
        this.subscriberId = subscriberId;
        this.interactionId = interactionId;
    }

    @Override
    public void run() {

        synchronized (lock) {
            notificationService.createNotification(publisherId,interactionId,subscriberId);
        }

    }
}
