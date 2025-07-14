package com.writenow.write.Repositories;


import com.writenow.write.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {



    @Query(
            value = "select * from notifications where subscriber_id = :subscriberId",
            nativeQuery = true
    )
    public List<Notification> fetchById(@Param("subscriberId") long subscriberId);
}
