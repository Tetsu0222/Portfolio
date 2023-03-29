package com.example.ec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ec.entity.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer>{

}
