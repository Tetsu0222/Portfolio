package com.example.test.service;

import java.util.List;

import com.example.test.entity.Pfc;

import lombok.Data;

@Data
public class Total {
	
	//各値の合計値
	private Integer totalCalorie      = 0;
	private Integer totalProtein      = 0;
	private Integer totalFat          = 0;
	private Integer totalCarbohydrate = 0;
	private Integer totalSalt         = 0;
	
	
	//PFCバランス
	private String proteinBalance      = "";
	private String fatBalance          = "";
	private String carbohydrateBalance = "";
	
	
	//各項目の合計値を算出
	public Total( List<Pfc> pfcList ) {
		
		//加算していく値を抽出
		pfcList.stream()
		.forEach( s -> this.add( s.getCalorie() , s.getProtein() , s.getFat() , s.getCarbohydrate() , s.getSalt() ));
		
		//PFCバランスをセット
		this.balance();
		
	}
	

	//各値を加算していき合計値を算出
	public void add( Integer calorie , Integer protein , Integer fat , Integer carbohydrate , Integer salt ) {
		
		this.totalCalorie      += calorie;
		this.totalProtein      += protein;
		this.totalFat          += fat;
		this.totalCarbohydrate += carbohydrate;
		this.totalSalt         += salt;
	}
	
	
	//PFCバランスを算出
	public void balance() {
		
		proteinBalance      = String.format( "%.2f%%" , (double) (totalProtein * 4 )     / totalCalorie * 100 );
		fatBalance          = String.format( "%.2f%%" , (double) (totalFat * 9     )     / totalCalorie * 100 );
		carbohydrateBalance = String.format( "%.2f%%" , (double) (totalCarbohydrate *4 ) / totalCalorie * 100 );
	}
	
}
