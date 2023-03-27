package com.example.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bakery.entity.OrderBakery;

public interface OrderBakeryRepository extends JpaRepository<OrderBakery, Integer>{

}
