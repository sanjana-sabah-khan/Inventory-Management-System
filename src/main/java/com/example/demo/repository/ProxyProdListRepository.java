package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProxyProdList;

@Repository
public interface ProxyProdListRepository extends JpaRepository <ProxyProdList, Integer> {
		
	
}
