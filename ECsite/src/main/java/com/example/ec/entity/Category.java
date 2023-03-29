package com.example.ec.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "goodscategory" )
@Data
@NoArgsConstructor
public class Category {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @Column( name = "name" )
    private String name;
    
    @OneToMany( mappedBy = "category", cascade = CascadeType.ALL )
    @OrderBy( "id asc")
    private List<Goods> goodsList= new ArrayList<>();
    
    
    public Category( Integer id ) {
    	
    	this.id = id;
    }

}
