package com.example.ec.form;

import com.example.ec.entity.Category;
import com.example.ec.entity.Goods;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoodsData {
	
	@NotBlank
    private String name;

	@Min( value = 0 )
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
