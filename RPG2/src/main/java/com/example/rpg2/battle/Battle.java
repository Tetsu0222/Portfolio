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
	
	
	//プレイアブルメンバーを管理
	Map<Integer, AllyData> partyMap;
	
	//エネミーメンバーを管理
	Map<Integer, MonsterData> monsterDataMap;
	
	//乱数計算に使用
	Random random = new Random();
	
	//プレイアブルメンバーの行動選択を管理
	Map<Integer,Target> targetMap;
	
	
	//コンストラクタ
	public Battle( List<AllyData> partyList , List<MonsterData> monsterDataList ) {
		
		//プレイアブルメンバーを生成
		this.partyMap = IntStream.range( 0 , partyList.size() )
							.boxed()
							.collect( Collectors.toMap( s -> s , s -> partyList.get( s ) ));
		
		//エネミーデータを生成
		this.monsterDataMap = IntStream.range( 0 , monsterDataList.size() )
				.boxed()
				.collect( Collectors.toMap( s -> s , s -> monsterDataList.get( s ) ));
		
		//プレイアブルメンバーの初期行動を最初のエネミーへの通常攻撃で設定（例外対策）
		this.targetMap = IntStream.range( 0 , partyList.size() )
								.boxed()
								.collect( Collectors.toMap( s -> s ,
										s -> new Target( partyList.get( s ) , monsterDataMap.get( 0 ) , 0 )));
	}
	
	
	//通常攻撃選択
	public void selectionAttack( Integer keys ,  Integer key ) {

		Target target = new Target( partyMap.get( keys ) , monsterDataMap.get( key ) , key );
		targetMap.put( keys , target );
	}
	
	
	//味方への魔法を選択
	public void selectionAllyMagic( Integer keys , Integer key , Magic magic ) {
		
		Target target = new Target ( partyMap.get( keys ) , partyMap.get( keys ) , key , magic );
		targetMap.put( keys , target );
	}

}
