package com.example.ec.form;

import com.example.ec.entity.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryData {
	
	@NotBlank
    private String name;
    
    
    public Category toEntity() {
    	
    	Category category = new Category();
    	category.setName( this.name );
    	
    	return category;
    }

}
