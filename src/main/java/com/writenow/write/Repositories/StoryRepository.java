package com.writenow.write.Repositories;


import com.writenow.write.Models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {


    @Query(
            value = "select * from stories where id = :id",
            nativeQuery = true
    )
    public Story fetchStoryById(@Param("id") long id);



    @Query(
            value = "select * from stories where writer_id = :writerId",
            nativeQuery = true
    )
    public List<Story> fetchStoriesByWriterId(@Param("writerId") long writerId);

}
