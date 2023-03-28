package com.example.bakery.form;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.bakery.entity.Customer;
import com.example.bakery.entity.OrderBakery;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerData {

    private Integer id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String address;
    
    @NotBlank
    private Date birth;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String tell;
    
    
    private List<OrderBakeryData> orderList;
    private OrderBakeryData newOrder;
    
    
    public CustomerData( Customer customer ) {
    	
    	//入力内容か検索内容から値を取得
    	this.id      = customer.getId();
    	this.address = customer.getAddress();
    	this.birth   = customer.getBirth();
    	this.email   = customer.getEmail();
    	this.tell    = customer.getTell();
    	
    	//注文履歴を参照
        this.orderList = new ArrayList<>();
        for ( OrderBakery order : customer.getOrderList() ) {
            this.orderList.add( new OrderBakeryData( order.getId() , order.getOrderDate() , order.getTimezone() ,
            		order.getDesiredDeliveryTime() , order.getDateOfShipment() , order.getDone() , order.getTotalPrice() , order.getGoods() ));
        }

        //注文履歴追加用
        newOrder = new OrderBakeryData();
    	
    }
    
    
    public Customer toEntity( CustomerData customerData ) {
    	
        Customer customer = new Customer();
        customer.setId      ( id      );
        customer.setAddress ( address );
        customer.setBirth   ( birth   );
        customer.setEmail   ( email   );
        customer.setTell    ( tell    );

        
        return customer;
        
    }
    
}
