package com.example.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todolist.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>{

}
