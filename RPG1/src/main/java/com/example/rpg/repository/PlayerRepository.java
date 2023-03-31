package com.example.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rpg.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>{

}
