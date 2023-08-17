package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderProductSelected;


@Repository
public interface OrderProductSelectedRepository extends JpaRepository <OrderProductSelected, Integer> {

	
	
}
