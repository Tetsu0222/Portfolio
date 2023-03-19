package com.example.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todolist.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
