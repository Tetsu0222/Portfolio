package com.example.bakery.form;

import com.example.bakery.entity.Bakery;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BakeryData {
	
    private Integer id;
    
    @NotBlank
    private String name;
    
    @Min( value = 0 )
    private Integer price;
    
    
    public Bakery toEntity() {
    	
    	Bakery bakery= new Bakery();
    	
    	bakery.setId   ( id    );
    	bakery.setName ( name  );
    	bakery.setPrice( price );
    	
    	return bakery;
    }

}
