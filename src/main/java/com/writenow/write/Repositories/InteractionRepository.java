package com.writenow.write.Repositories;


import com.writenow.write.Models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction,Long> {
}
