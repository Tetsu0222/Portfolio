package com.example.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rpg.entity.Magic;

@Repository
public interface MagicRepository extends JpaRepository<Magic, Integer>{

}
