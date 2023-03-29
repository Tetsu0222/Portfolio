package com.example.ec.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.ec.entity.Goods;
import com.example.ec.repository.GoodsRepository;

import lombok.Data;

@Data
public class Basket {
	
	
	//フィールド
	Long totalPrice;
	
	//カート内の商品数保持
	private Map<String , Long> quantityMap = new HashMap<>();
	
	//カート内の金額を保持
	private Map<String , Long> priceMap = new HashMap<>();
	
	private GoodsRepository goodsRepository;
	
	
	
	
	//カートに商品を追加
	public void addGoods( Goods goods ,
			GoodsRepository goodsRepository ,
			Long quantity ) {
		
		this.goodsRepository = goodsRepository;
		String name  = goods.getName();
		Long   total = 0L;
				
		//すでにカートに入っている商品をさらに追加
		if( quantityMap.get( name ) != null ) {
			total += quantityMap.get( name ) + quantity;
			
		//カートに新たに商品を追加
		}else{
			total += quantity;
		}
		
		//商品と個数をマップで格納
		quantityMap.put( name , total );
		
		this.totalcalc();
		
	}
	
	
	//カート内の合計金額を算出
	public void totalcalc() {
		
		List<Goods> list = goodsRepository.findAll();
		
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
			
				//商品別の合計金額をマップへ格納
				priceMap.put( name , calculation );
			}
			
		}
		
		//カート内の合計金額を算出
		List<Long> totalList = new ArrayList<>( priceMap.values() );
		totalPrice = totalList.stream()
				.collect( Collectors.summingLong( s -> s ));
			
	}
	
	
	//カートの商品数を変更
	public void changeBasket( String changeName , Long quantity ) {
		
		quantityMap.put( changeName , quantity );
		
		this.totalcalc();
	}
	

	//カートから商品を削除
	public void deleteBasket( String deleteName ) {
		
		//各マップから消去
		quantityMap.remove( deleteName );
		priceMap.remove( deleteName );
		
		//カート内の合計金額を算出
		List<Long> totalList = new ArrayList<>( priceMap.values() );
		totalPrice = totalList.stream()
				.collect( Collectors.summingLong( s -> s ));
		
	}
	

}
