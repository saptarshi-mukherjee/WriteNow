package com.writenow.write.Repositories;


import com.writenow.write.Models.Moderator;
import com.writenow.write.Projections.ModeratorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator,Long> {



    @Query(
            value = "select users.id, users.full_name, users.`status`, users.email, moderators.contest_count, moderators.moderator_status \n" +
                    "from users\n" +
                    "join moderators\n" +
                    "on users.id=moderators.id\n" +
                    "where users.full_name=:fullName",
            nativeQuery = true
    )
    public List<ModeratorProjection> fetchModeratorByName(@Param("fullName") String fullName);


//    @Query(
//            value = "select * from moderators where id = :id",
//            nativeQuery = true
//    )
//    public Moderator fetchModeratorById(@Param("id") long id);

    public Moderator findById(long id);
}
