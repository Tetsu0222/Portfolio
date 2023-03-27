package com.example.bakery.entity;

import java.sql.Date;
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

@Entity
@Table( name = "customer" )
@Data
public class Customer {

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
    
    @OneToMany( mappedBy = "customer" , cascade = CascadeType.ALL )
    @OrderBy( "id asc" )
    private List<OrderBakery> orderList= new ArrayList<>();
    
    
    //Customerへの参照設定
    public void addOrder( OrderBakery orderBakery ) {
    	orderBakery.setCustomer( this );
        orderList.add( orderBakery );
    }
    
}
