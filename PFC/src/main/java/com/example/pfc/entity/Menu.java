package com.example.pfc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table( name = "menu" )
@Data
public class Menu {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @Column( name = "name" )
    private String name;
    
    @Column( name = "calorie" )
    private Double calorie;
    
    @Column( name = "protein" )
    private Double protein;
    
    @Column( name = "fat" )
    private Double fat;
    
    @Column( name = "carbohydrate" )
    private Double carbohydrate;
    
    @Column( name = "salt" )
    private Double salt;

}
