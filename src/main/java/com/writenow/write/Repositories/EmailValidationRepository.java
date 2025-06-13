package com.writenow.write.Repositories;


import com.writenow.write.Models.EmailValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailValidationRepository extends JpaRepository<EmailValidation,Long> {


    @Query(
            value = "select * from validations",
            nativeQuery = true
    )
    public List<EmailValidation> fetchAllOtp();


    @Query(
            value = "select * from validations where email = :email and otp = :otp and expiry>now()",
            nativeQuery = true
    )
    public EmailValidation fetchValidOtp(@Param("email") String email, @Param("otp") int otp);
}
