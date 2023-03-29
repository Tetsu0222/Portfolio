package com.example.ec.form;

import com.example.ec.entity.Category;
import com.example.ec.entity.Goods;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GoodsData {
	
    private String name;

    private Integer price;
    
    @Min( value = 1 )
    private Integer categoryId;
    
    
    public Goods toEntity() {
    	
    	Goods goods = new Goods();
    	goods.setName ( this.name  );
    	goods.setPrice( this.price );
    	goods.setCategory( new Category( this.categoryId ) );
    	
    	return goods;
    }

}
