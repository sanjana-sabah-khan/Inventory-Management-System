package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Registration;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
	
	
	@Query("SELECT r FROM registration r WHERE r.admin_email= :email")
    public Registration findByEmail (@Param("email") String email);
	

}
