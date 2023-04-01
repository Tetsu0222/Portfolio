package com.example.rpg2.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class Battle {
	
	
	//エネミーを管理
	MonsterData monsterData;
	
	//プレイアブルメンバーを管理
	Map<Integer, AllyData> partyMap;
	
	//乱数計算に使用
	Random random = new Random();
	
	//プレイアブルメンバーの行動選択を管理
	Map<Integer,Selection> selectionMap;
	
	
	//コンストラクタ
	public Battle( List<AllyData> partyList , MonsterData monsterData ) {
		
		//エネミーデータを生成(単体から複数へ改修予定)
		this.monsterData = monsterData;
		
		//プレイアブルメンバーを生成
		this.partyMap = IntStream.range( 0 , partyList.size() )
							.boxed()
							.collect( Collectors.toMap( s -> s , s -> partyList.get( s ) ));
	}
	
	
	//プレイアブルメンバーの行動決定(通常攻撃選択)
	public void selectionAttack( int key ) {
		
		//選択Mapの初期化
		if( selectionMap == null ) {
			selectionMap = new HashMap<>();
		}
		
		Attack attack = new Attack( this.getPartyMap().get( key ) , monsterData );
		selectionMap.put( key , attack );
		
	}

}
