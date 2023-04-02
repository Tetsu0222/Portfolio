package com.example.rpg2.battle;

import java.util.ArrayList;
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
	
	//敵の行動対象用を管理
	Map<Integer,AllyData> targetMapEnemy;
	
	List<Integer> targetList;
	
	
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
		
		this.targetMapEnemy = partyMap;
		
		this.targetList = new ArrayList<>( targetMapEnemy.keySet() );
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
	
	
	//戦闘不能時の処理
	
	
	//味方側の戦闘行動を処理
	public Long startBattle() {

		for( int i = 0 ; i < partyMap.size() ; i++ ) {
			Action action = new Action();
			AllyData allyData = partyMap.get( i );
			Integer target	  = targetMap.get( i ).getSelectionId();
			String movementPattern = targetMap.get( i ).getCategory();
			
				//通常攻撃の処理
				if( movementPattern.equals( "attack" )) {
					MonsterData monsterData = action.actionAttack( allyData , monsterDataMap.get( target ) );
					
					if( monsterData.getCurrentHp() == 0 ) {
						monsterData.setSurvival( 0 );
						monsterDataMap.put( target , monsterData );
					}else{
						monsterDataMap.put( target , monsterData );
					}
				
				//回復魔法の処理
				}else if( movementPattern.equals( "recoverymagic" )) {
					
					if( targetMap.get( i ).getExecutionMagic().getMp() > allyData.getCurrentMp() ) {
						action.noAction();
						
					}else {
						AllyData receptionAllyData = action.actionRecoveryMagic( allyData , partyMap.get( target ) , targetMap.get( i ).getExecutionMagic() );
						partyMap.put( target , receptionAllyData );
						allyData = action.consumptionMp( allyData , targetMap.get( i ).getExecutionMagic() );
						partyMap.put( i , allyData );
					}
				
				//攻撃魔法の処理
				}else if( movementPattern.equals( "attackmagic" )) {
					
					if( targetMap.get( i ).getExecutionMagic().getMp() > allyData.getCurrentMp() ) {
						action.noAction();
						
					}else{
						MonsterData monsterData = action.actionAttackMagic( allyData , monsterDataMap.get( target )  , targetMap.get( i ).getExecutionMagic() );
						
						if( monsterData.getCurrentHp() == 0 ) {
							monsterData.setSurvival( 0 );
							monsterDataMap.put( target , monsterData );
						}else{
							monsterDataMap.put( target , monsterData );
						}
						
						allyData = action.consumptionMp( allyData , targetMap.get( i ).getExecutionMagic() );
						partyMap.put( i , allyData );
					}
				}

		}
		
		//敵の生存数を判定
		Long numberOfEnemies = IntStream.range( 0 , monsterDataMap.size() )
				.map( s -> monsterDataMap.get( s ).getSurvival() )
				.filter( s -> s == 1 )
				.count();
		
		return numberOfEnemies;
	}
	
	
	//敵の戦闘行動を処理
	public int enemyBattle() {
		
		
		
		for( int i = 0 ; i < monsterDataMap.size() ; i++ ) {
			MonsterData monsterData = monsterDataMap.get( i );
			EnemyAction enemyAction = new EnemyAction();
			enemyAction.decision( monsterData );
			
			if( monsterData.getSurvival() == 0 ) {
				enemyAction.setPattern( "miss" );
			}
			
			//物理攻撃処理
			if( enemyAction.getPattern().equals( "attackskill" )) {
				
				/*
				//単体攻撃を処理
				if( enemyAction.getRange().equals( "single" )) {
					AllyData allyData = enemyAction.attackSkillSingle( targetMapEnemy );
					if( allyData.getSurvival() == 0 ) {
						targetMapEnemy.remove( enemyAction.getTargetId() );
						targetMap.put( enemyAction.getTargetId() , new Target( enemyAction.getTargetId() ) );
						partyMap.put( enemyAction.getTargetId() , allyData );
					}else{
						targetMapEnemy.put( enemyAction.getTargetId() , allyData );
						partyMap.put( enemyAction.getTargetId() , allyData );
					}
					
				
				//全体攻撃を処理(ここがおかしい)
				}else{
					List<Integer> targetList = new ArrayList<>( targetMapEnemy.keySet() );
					for( int j = 0 ; j < targetList.size() ; j++ ) {
						int targetId = targetList.get( j );
						AllyData allyData = enemyAction.attackSkillWhole( targetMapEnemy.get( targetId ) );
						if( allyData.getCurrentHp() == 0 ) {
							targetMapEnemy.remove( enemyAction.getTargetId() );
							targetMap.put( enemyAction.getTargetId() , new Target( enemyAction.getTargetId() ) );
							partyMap.put( enemyAction.getTargetId() , allyData );
						}else{
							targetMapEnemy.put( enemyAction.getTargetId() , allyData );
							partyMap.put( enemyAction.getTargetId() , allyData );
						}
					}
				}
				*/
				
				//---テスト
				AllyData allyData = enemyAction.attackSkillSingle( targetMapEnemy , targetList );
				if( allyData.getSurvival() == 0 ) {
					targetMapEnemy.remove( enemyAction.getTargetId() );
					this.targetList = new ArrayList<>( targetMapEnemy.keySet() );
					targetMap.put( enemyAction.getTargetId() , new Target( enemyAction.getTargetId() ) );
					partyMap.put( enemyAction.getTargetId() , allyData );
				}else{
					partyMap.put( enemyAction.getTargetId() , allyData );
				}
			
			//ミス系
			}else if( enemyAction.getPattern().equals( "miss" )) {
				enemyAction.noAction();
			}
		}
		
		//味方の生存者数を判定
		int numberOfAllys = targetMapEnemy.size();
		
		return numberOfAllys;
	}

}
