package com.example.pfc.form;

import com.example.pfc.entity.Food;
import com.example.pfc.entity.Menu;

import lombok.Data;

@Data
public class MenuData {
	
    private String name;
    
    private Double calorie = 0.0;
    
    private Double protein = 0.0;
    
    private Double fat = 0.0;
    
    private Double carbohydrate = 0.0;
    
    private Double salt = 0.0;
    
    
    public void addFood( Food food , Integer gram ) {
    	
    	this.calorie += food.getCalorie() * gram;
    	this.protein += food.getProtein() * gram;
    	this.fat	 += food.getFat()	  * gram;
    	this.carbohydrate += food.getCarbohydrate() * gram;
    	this.salt 	 += food.getSalt() * gram;
    	
    }
    
    
    public Menu toEntity() {
    	
    	Menu menu = new Menu();
    	
    	menu.setName   ( this.getName()    );
    	menu.setCalorie( this.getCalorie() );
    	menu.setProtein( this.protein      );
    	menu.setFat    ( this.getFat()     );
    	menu.setCarbohydrate( this.getCarbohydrate() );
    	menu.setSalt   ( this.getSalt()    );
    	
    	return menu;
    }

}
