package com.example.bakery.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table( name = "orderbakerytest" )
@Data
public class OrderBakeryTest {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @Column( name = "name" )
    private String name;
    
    @Column( name = "address" )
    private String address;
    
    @Column( name = "birth" )
    private Date birth;
    
    @Column( name = "email" )
    private String email;
    
    @Column( name = "tell" )
    private String tell;
    
    @Column( name = "totalprice" )
    private Integer totalPrice;
    
    @Column( name = "goods" )
    private String goods;
	
	
}
