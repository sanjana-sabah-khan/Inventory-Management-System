package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Brand;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
	
	@Query("SELECT b FROM brand b WHERE b.brand_name= :bn")
    public List <Brand> findByBrandname (@Param("bn") String name);

	
}
