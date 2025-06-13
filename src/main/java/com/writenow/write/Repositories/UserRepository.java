package com.writenow.write.Repositories;


import com.writenow.write.Models.User;
import com.writenow.write.Projections.UserIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    @Query(
            value = "select users.id as id from users where users.full_name = :fullName",
            nativeQuery = true
    )
    public List<UserIdProjection> fetchByName(@Param("fullName") String fullName);


    @Modifying
    @Query(
            value = "update users\n" +
                    "set `status`=0\n" +
                    "where users.id=:userId",
            nativeQuery = true
    )
    public void updateUserEmailStatus(@Param("userId") long userId);
}
