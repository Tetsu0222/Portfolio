package com.example.rpg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table( name = "enemy" )
@Data
public class Enemy {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @Column( name = "name" )
    private String name;
    
    @Column( name = "hp" )
    private Integer hp;
    
    @Column( name = "mp" )
    private Integer mp;

    @Column( name = "atk" )
    private Integer atk;
    
    @Column( name = "def" )
    private Integer def;
    
    @Column( name = "pattern" )
    private Integer pattern;
    
    @Column( name = "actions" )
    private Integer actions;
    
}
