package com.example.pfc.service;

import java.util.List;

import com.example.pfc.entity.Food;

import lombok.Data;

@Data
public class Total {
	
	//各値の合計値
	private Double totalCalorie      = 0.0;
	private Double totalProtein      = 0.0;
	private Double totalFat          = 0.0;
	private Double totalCarbohydrate = 0.0;
	private Double totalSalt         = 0.0;
	
	
	//PFCバランス
	private String proteinBalance      = "";
	private String fatBalance          = "";
	private String carbohydrateBalance = "";
	
	
	//各項目の合計値を算出
	public Total( List<Food> pfcList ) {
		
		//加算していく値を抽出
		pfcList.stream()
		.forEach( s -> this.add( s.getCalorie() , s.getProtein() , s.getFat() , s.getCarbohydrate() , s.getSalt() ));
		
		//PFCバランスをセット
		this.balance();
		
	}
	

	//各値を加算していき合計値を算出
	public void add( Double calorie , Double protein , Double fat , Double carbohydrate , Double salt ) {
		
		this.totalCalorie      += calorie;
		this.totalProtein      += protein;
		this.totalFat          += fat;
		this.totalCarbohydrate += carbohydrate;
		this.totalSalt         += salt;
	}
	
	
	//PFCバランスを算出
	public void balance() {
		
		proteinBalance      = String.format( "%.2f%%" , (totalProtein * 4 )     / totalCalorie * 100 );
		fatBalance          = String.format( "%.2f%%" , (totalFat * 9     )     / totalCalorie * 100 );
		carbohydrateBalance = String.format( "%.2f%%" , (totalCarbohydrate *4 ) / totalCalorie * 100 );
	}
	
}
