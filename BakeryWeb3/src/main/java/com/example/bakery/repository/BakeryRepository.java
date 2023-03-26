package com.example.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bakery.entity.Bakery;

@Repository
public interface BakeryRepository extends JpaRepository<Bakery, Integer>{

}
