package com.example.rpg2.battle;

import java.util.Random;

import com.example.rpg2.entity.Magic;

import lombok.Data;

@Data
public class Action {
	
	Random random = new Random();
	
	
	//通常攻撃
	public MonsterData actionAttack( AllyData allyData , MonsterData monsterData ) {
		
		//(攻撃力-防御力/2) + 乱数 = ダメージ
		Integer damage = (allyData.getCurrentATK() - (monsterData.getCurrentDEF() / 2)) 
				+ random.nextInt( allyData.getCurrentATK()/2 );
		
		if( damage < 0 ) {
			damage = 0;
		}
		
		Integer HP = monsterData.getCurrentHp() - damage;
		
		if( HP < 0 ) {
			monsterData.setCurrentHp( 0 );
		}else{
			monsterData.setCurrentHp( HP );
		}
		return monsterData;
	}
	
	
	//味方への魔法
	public void action( AllyData allyData , AllyData receptionAllyData , Magic magic ) {
		
		//回復魔法を発動
		if( magic.getCategory().equals( "recoverymagic" ) && magic.getPercentage() == 0 ) {
			int HP = receptionAllyData.getCurrentHp();
			HP += magic.getPoint() + random.nextInt( magic.getPoint() );
			
			if( receptionAllyData.getMaxHP() < HP ) {
				HP = receptionAllyData.getMaxHP();
			}
			receptionAllyData.setCurrentHp( HP );
			
		}else if( magic.getCategory().equals( "recoverymagic" ) ){
			double HP = receptionAllyData.getMaxHP() * magic.getPercentage();
			receptionAllyData.setCurrentHp( (int)HP );
		}
		
		
		//MP消費処理
		int MP = allyData.getCurrentMp();
		MP -= magic.getMp();
		allyData.setCurrentMp( MP );
		
	}
	

}