package com.example.pfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfc.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{

}
