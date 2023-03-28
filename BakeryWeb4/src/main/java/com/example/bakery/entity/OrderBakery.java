package com.example.bakery.entity;

import java.sql.Date;

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
@Table( name = "orderbakery" )
@Data
public class OrderBakery {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    
    @ManyToOne
    @JoinColumn( name = "c_id" )
    private Customer customer;
    
    @Column( name = "orderdate" )
    private Date orderDate;
    
    @Column( name = "desireddeliverytime" )
    private Date desiredDeliveryTime;
    
    @Column( name = "timezone" )
    private Integer timezone;
    
    @Column( name = "dateofshipment" )
    private Date dateOfShipment;
    
    @Column( name = "done" )
    private String done;
    
    @Column( name = "totalprice" )
    private Integer totalPrice;
    
    @Column( name = "goods" )
    private String goods;
    
}
