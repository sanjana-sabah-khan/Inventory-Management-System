package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository <Order, Integer> {
	
	
	@Query("SELECT o FROM orders o WHERE o.order_code= :c")
    public Order findByOrder_code(@Param("c") int order_code);
	
	
	
	
}
