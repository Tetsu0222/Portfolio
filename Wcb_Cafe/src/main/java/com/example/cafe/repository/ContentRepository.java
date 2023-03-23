package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.entity.Content;

public interface ContentRepository extends JpaRepository<Content, Integer> {

}
