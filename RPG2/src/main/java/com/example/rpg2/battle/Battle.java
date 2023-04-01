package com.example.rpg2.battle;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.rpg2.entity.Magic;

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
		
		//プレイアブルメンバーの初期行動を設定（例外対策）
		this.selectionMap = IntStream.range( 0 , partyList.size() )
								.boxed()
								.collect( Collectors.toMap( s -> s , s -> new Attack( partyList.get( s ) , monsterData )));
	}
	
	
	//プレイアブルメンバーの行動決定(通常攻撃選択)
	public void selectionAttack( int key ) {
		
		Attack attack = new Attack( this.getPartyMap().get( key ) , monsterData );
		selectionMap.put( key , attack );
		
	}
	
	
	//魔法を選択
	public void selectionMagic( Integer key , Magic magic ) {
		
		MagicData magicData = new MagicData( magic , key );
		selectionMap.put( key , magicData );
		
	}

}
