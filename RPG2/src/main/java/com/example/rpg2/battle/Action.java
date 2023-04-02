package com.example.rpg2.battle;

import java.util.Random;

import com.example.rpg2.entity.Magic;

import lombok.Data;

@Data
public class Action {
	
	private Integer recovery;
	private Integer damage  ;
	private String  damageMessage;
	private String  recoveryMessage;
	
	Random random = new Random();
	
	//通常攻撃
	public MonsterData actionAttack( AllyData allyData , MonsterData monsterData ) {
		
		//(攻撃力-防御力/2) + 乱数 = ダメージ
		this.damage = (allyData.getCurrentATK() - (monsterData.getCurrentDEF() / 2)) 
				+ ( random.nextInt( allyData.getCurrentATK() ) / 2 );
		
		if( damage < 0 ) {
			damage = 0;
		}
		
		Integer HP = monsterData.getCurrentHp() - damage;
		
		if( HP < 0 ) {
			monsterData.setCurrentHp( 0 );
			this.damageMessage = damage + "のダメージを与えた!!";
		}else{
			monsterData.setCurrentHp( HP );
			this.damageMessage = damage + "のダメージを与えた!!";
		}
		
		return monsterData;
	}
	
	
	//味方への魔法
	public AllyData actionRecoveryMagic( AllyData allyData , AllyData receptionAllyData , Magic magic ) {
		
		//回復魔法を発動
		if( magic.getCategory().equals( "recoverymagic" ) && magic.getPercentage() == 0 ) {
			int HP = receptionAllyData.getCurrentHp();
			this.recovery = magic.getPoint() + random.nextInt( magic.getPoint() );
			
			HP += recovery;
			
			if( receptionAllyData.getCurrentHp() < HP ) {
				HP = receptionAllyData.getMaxHP();
			}
			receptionAllyData.setCurrentHp( HP );
			this.recoveryMessage = recovery + "のHPを回復した";
			
		}else if( magic.getCategory().equals( "recoverymagic" ) ){
			double HP = receptionAllyData.getMaxHP() * magic.getPercentage();
			receptionAllyData.setCurrentHp( (int)HP );
		}
		
		return receptionAllyData;
		
	}
	
	
	//攻撃魔法
	public MonsterData actionAttackMagic( AllyData allyData , MonsterData monsterData , Magic magic) {
		
		
		//魔法威力 + 乱数 = ダメージ
		Integer damage = ( magic.getPoint() + ( random.nextInt( magic.getPoint() ) / 4 ) );
		
		if( damage < 0 ) {
			damage = 0;
		}
		
		Integer HP = monsterData.getCurrentHp() - damage;
		
		if( HP < 0 ) {
			monsterData.setCurrentHp( 0 );
			this.damageMessage = damage + "のダメージを与えた!!";
		}else{
			monsterData.setCurrentHp( HP );
			this.damageMessage = damage + "のダメージを与えた!!";
		}
		
		
		return monsterData;
	}
	
	
	
	//MP消費処理
	public AllyData consumptionMp( AllyData allyData , Magic magic ) {
		
		int MP = allyData.getCurrentMp();
		MP -= magic.getMp();
		allyData.setCurrentMp( MP );
		
		return allyData;
	}
	
	
	//死亡時の処理
	public void noAction() {
		//明示的に何も処理しない。
	}
	

}
