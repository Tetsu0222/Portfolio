package com.example.ec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table( name = "goods" )
@Data
public class Goods {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @Column( name = "name" )
    private String name;
    
    @Column( name = "price" )
    private Integer price;
    
    @ManyToOne
    @JoinColumn( name = "c_id" )
    private Category category;

}
