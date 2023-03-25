package com.example.bakery.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.bakery.entity.Bakery;
import com.example.bakery.repository.BakeryRepository;

import lombok.Data;

@Data
public class Basket {
	
	
	//フィールド
	Long totalPrice;
	
	//カートに商品の受け入れ用
	private List<Bakery> bakeryList = new ArrayList<>();
	
	//カート内の商品数保持
	private Map<String , Long> quantityMap = new HashMap<>();
	
	//カート内の金額を保持
	private Map<String , Long> priceMap = new HashMap<>();
	
	private BakeryRepository bakeryRepository;
	
	
	
	
	//カートに商品を追加
	public void addBakery( Bakery bakery ,
			BakeryRepository bakeryRepository ) {
		
		this.bakeryRepository = bakeryRepository;
		
		bakeryList.add( bakery );
		
		//カート内を整理
		Long total = bakeryList.stream()
				.map( s -> s.getName() )
				.filter( s -> s.equals( bakery.getName() ) )
				.count();
		
		//商品と個数をマップで格納
		quantityMap.put( bakery.getName() , total );
		
		this.totalcalc();
		
	}
	
	
	//カート内の合計金額を算出
	public void totalcalc() {
		
		List<Bakery> list = bakeryRepository.findAll();
		
		for( int i = 0 ; i < list.size() ; i++ ) {
			String  name  = list.get( i ).getName();
			Integer price = list.get( i ).getPrice();
			
			Long quantity = quantityMap.get( name );
			Long calculation = 0L;
				
			//未選択商品による例外対策
			if( quantity == null ) {
				quantity = 0L;
			}
			
			if( quantity > 0 ) {
				calculation = price * quantity;
			
				//商品別の金額をマップへ格納
				priceMap.put( name , calculation );
			}
			
		}
		
		//カート内の合計金額を算出
		List<Long> totalList = new ArrayList<>( priceMap.values() );
		totalPrice = totalList.stream()
				.collect( Collectors.summingLong( s -> s ));
			
	}
	
	
	//カートの商品数を変更
	public void changeBasket( Bakery bakery , Long quantity ) {
		
		quantityMap.put( bakery.getName() , quantity );
		this.totalcalc();
		
	}

	//カートから商品を削除
	public void deleteBasket( String deleteName ) {
		
		quantityMap.remove( deleteName );
		priceMap.remove( deleteName );
		
		//カート内の合計金額を算出
		List<Long> totalList = new ArrayList<>( priceMap.values() );
		totalPrice = totalList.stream()
				.collect( Collectors.summingLong( s -> s ));
		
	}

}
