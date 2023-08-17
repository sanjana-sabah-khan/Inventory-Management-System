package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OTPNumber;


@Repository
public interface OTPNumRepository extends JpaRepository<OTPNumber, Integer> {

	@Query("SELECT ot FROM otp ot WHERE ot.otp_email= :email")
    public List <OTPNumber> findOTPByEmail (@Param("email") String email);
	
	
}
