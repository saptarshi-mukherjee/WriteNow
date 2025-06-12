package com.writenow.write.Repositories;


import com.writenow.write.Models.Writer;
import com.writenow.write.Projections.WriterProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriterRepository extends JpaRepository<Writer,Long> {


    @Query(
            value = "select users.id, full_name, email, `status`, url \n" +
                    "from users\n" +
                    "join writers\n" +
                    "on users.id=writers.id\n" +
                    "where users.full_name=:fullName",
            nativeQuery = true
    )
    public List<WriterProjection> fetchWriterByName(@Param("fullName") String fullName);
}
