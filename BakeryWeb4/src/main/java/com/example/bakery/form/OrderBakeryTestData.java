package com.example.bakery.form;

import java.sql.Date;

import com.example.bakery.entity.OrderBakeryTest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderBakeryTestData {
	
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
