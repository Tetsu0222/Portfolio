package com.example.bakery.form;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBakeryData {


    private Integer id;
    
    private Date orderDate;

    private Integer timezone;
    
    private Date desiredDeliveryTime;
    
    private Date dateOfShipment;
    
    private String done;
    
    private Integer totalPrice;
    
    private String goods;
    
}
