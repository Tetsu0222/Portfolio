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
	Map<Integer,AllyData> partyMap;
	
	//エネミーメンバーを管理
	Map<Integer,MonsterData> monsterDataMap;
	
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
										s -> new Target( monsterDataMap.get( 0 )  , s  , 0 )));
	}
	
	
	//通常攻撃を選択
	public void selectionAttack( Integer myKeys ,  Integer key ) {

		Target target = new Target( monsterDataMap.get( key ) , myKeys , key );
		targetMap.put( myKeys , target );
	}
	
	
	//味方への魔法を選択
	public void selectionAllyMagic( Integer myKeys , Integer key , Magic magic ) {
		
		Target target = new Target ( partyMap.get( key ) , myKeys , key , magic );
		targetMap.put( myKeys , target );
	}
	
	
	//攻撃魔法を選択
	public void selectionMonsterMagic( Integer myKeys , Integer key , Magic magic ) {
		
		Target target = new Target( monsterDataMap.get( key ) , myKeys , key , magic );
		targetMap.put( myKeys , target );
	}
	
	
	public Long startBattle() {

		for( int i = 0 ; i < partyMap.size() ; i++ ) {
			Action action = new Action();
			AllyData allyData = partyMap.get( i );
			Integer target	  = targetMap.get( i ).getSelectionId();
			String movementPattern = targetMap.get( i ).getCategory();
			
			//通常攻撃の処理
			if( movementPattern.equals( "attack" )) {
				MonsterData monsterData = action.actionAttack( allyData , monsterDataMap.get( target ));
				
				if( monsterData.getCurrentHp() == 0 ) {
					monsterData.setSurvival( 0 );
					
				}else{
					monsterDataMap.put( target , monsterData );
				}
			}
		}
		
		//敵の生存数を判定
		Long numberOfEnemies = IntStream.range( 0 , monsterDataMap.size() )
				.map( s -> monsterDataMap.get( s ).getSurvival() )
				.filter( s -> s > 0 )
				.count();
		
		return numberOfEnemies;
	}

}
