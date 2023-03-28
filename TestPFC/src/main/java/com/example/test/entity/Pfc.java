package com.example.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table( name = "pfc" )
@Data
public class Pfc {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @Column( name = "name" )
    private String name;
    
    @Column( name = "calorie" )
    private Integer calorie;
    
    @Column( name = "protein" )
    private Integer protein;
    
    @Column( name = "fat" )
    private Integer fat;
    
    @Column( name = "carbohydrate" )
    private Integer carbohydrate;
    
    @Column( name = "salt" )
    private Integer salt;

}
