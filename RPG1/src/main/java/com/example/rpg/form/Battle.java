package com.example.rpg.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.rpg.entity.Enemy;
import com.example.rpg.entity.Player;

import lombok.Data;

@Data
public class Battle {
	
	private Integer playerHp;
	private Integer playerMp;
	private Integer playerATK;
	private Integer playerDEF;
	
	private Integer enemyHp;
	private Integer enemyMp;
	private Integer enemyATK;
	private Integer enemyDEF;
	
	private List<String> battleMessage;
	
	private Player player;
	private Enemy enemy;
	
	
	public Battle( Player player , Enemy enemy ) {
		
		this.player = player;
		this.enemy  = enemy;
		
		this.playerHp  = player.getHp();
		this.playerMp  = player.getMp();
		this.playerATK = player.getAtk();
		this.playerDEF = player.getDef();
		
		this.enemyHp  = enemy.getHp();
		this.enemyMp  = enemy.getMp();
		this.enemyATK = enemy.getAtk();
		this.enemyDEF = enemy.getDef();
	}
	
	
	//ダメージを受ける計算
	public void damegeCalculationPlayer( Integer damage ) {
		
		Random random = new Random();
		damage += random.nextInt( 10 ) - playerDEF;
		
		this.playerHp -= damage;
		battleMessage.add( enemy.getName() + "の攻撃‼" );
		battleMessage.add( player.getName() + "は" + damage + "のダメージを受けた!!" );
		
		if( playerHp <= 0 ) {
			this.playerHp = 0;
			battleMessage.add( player.getName() + "は負けてしまった…" );
		}
	}
	
	
	//回復する計算
	public void recovery( Integer recovery , String magicName , Integer magicMp) {
		
		if( playerMp - magicMp > 0 ) {
			Random random = new Random();
			recovery += random.nextInt( 5 );
			
			this.playerHp += recovery;
			battleMessage.add( player.getName() + "は" + magicName + "を発動した!!!" );
			battleMessage.add( player.getName() + "は" + recovery + "回復した!!!" );
			
			if( playerHp > player.getHp() ) {
				this.playerHp = player.getHp();
			}
			
			this.playerMp -= magicMp;
			
		}else{
			battleMessage.add( player.getName() + "は" + magicName + "を発動した!!!" );
			battleMessage.add( "ミス!!!･･･MPが足りません。" );
		}

	}
	
	
	//ダメージを与える計算
	public void damegeCalculationEnemy( Integer perpetrator ) {
		
		Random random = new Random();
		perpetrator += random.nextInt( 10 ) - enemyDEF;
		
		this.enemyHp -= perpetrator;
		battleMessage.add( player.getName() + "の攻撃‼" );
		battleMessage.add( enemy.getName() + "に" + perpetrator + "のダメージ!!!" );
		
		if( enemyHp <= 0 ) {
			this.enemyHp = 0;
			battleMessage.add( enemy.getName() + "を倒した!!!" );
		}
	}
	
	public void damegeCalculationEnemy( Integer perpetrator , String magicName , Integer magicMp ) {
		
		if( playerMp - magicMp > 0 ) {
			Random random = new Random();
			perpetrator += random.nextInt( 10 );
			
			this.enemyHp -= perpetrator;
			battleMessage.add( player.getName() + "は" + magicName + "を発動した!!!" );
			battleMessage.add( enemy.getName() + "に" + perpetrator + "のダメージ!!!" );
			
			this.playerMp -= magicMp;
			
			if( enemyHp <= 0 ) {
				this.enemyHp = 0;
				battleMessage.add( enemy.getName() + "を倒した!!!" );
			}
			
		}else{
			battleMessage.add( player.getName() + "は" + magicName + "を発動した!!!" );
			battleMessage.add( "ミス!!!･･･MPが足りません。" );
		}
	}
	
	
	//初回表示メッセージ
	public void startMessage( String enemyName ) {
		battleMessage = new ArrayList<>();
		battleMessage.add( enemyName + "が現れた!!!" );
	}
	

	//ターン毎に呼び出されてログを再表示させるためのメソッド
	public void resetMessage() {
		this.battleMessage.clear();
	}
}
