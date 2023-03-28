package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.entity.Pfc;

@Repository
public interface PfcRepository extends JpaRepository<Pfc, Integer>{

}
