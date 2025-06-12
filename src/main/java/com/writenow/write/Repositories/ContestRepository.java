package com.writenow.write.Repositories;

import com.writenow.write.Models.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> {

    @Query(
            value = "select * from contests where id = :id",
            nativeQuery = true
    )
    public Contest fetchContestById(@Param("id") long id);
}
