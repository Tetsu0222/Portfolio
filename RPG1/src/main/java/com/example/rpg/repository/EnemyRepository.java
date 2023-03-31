package com.example.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rpg.entity.Enemy;

@Repository
public interface EnemyRepository extends JpaRepository<Enemy, Integer>{

}
