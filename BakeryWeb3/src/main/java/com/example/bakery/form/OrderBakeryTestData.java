package com.example.bakery.form;

import java.sql.Date;

import com.example.bakery.entity.OrderBakeryTest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderBakeryTestData {
	
    private Integer id;
    private String name;
    private String address;
    private Date birth;
    private String email;
    private String tell;
    private Integer totalPrice;
    private String goods;
    
    
    
    public OrderBakeryTest toEntity( OrderBakeryTestData orderBakeryTestData , 
						    		 int totalprice , 
						    		 String goods ) {
    	
    	OrderBakeryTest orderBakeryTest = new OrderBakeryTest();
    	
    	orderBakeryTest.setId        ( id         );
    	orderBakeryTest.setName      ( name       );
    	orderBakeryTest.setAddress   ( address    );
    	orderBakeryTest.setBirth     ( birth      );
    	orderBakeryTest.setEmail     ( email      );
    	orderBakeryTest.setTell      ( tell       );
    	orderBakeryTest.setTotalPrice( totalprice );
    	orderBakeryTest.setGoods     ( goods      );
        
        return orderBakeryTest;
        
    }

}
