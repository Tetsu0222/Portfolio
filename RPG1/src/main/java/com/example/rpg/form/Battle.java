package com.example.rpg.form;

import java.util.ArrayList;
import java.util.List;

import com.example.rpg.entity.Enemy;
import com.example.rpg.entity.Player;

import lombok.Data;

@Data
public class Battle {
	
	private Integer playerHp;
	private Integer playerMp;
	
	private Integer enemyHp;
	private Integer enemyMp;
	
	private List<String> battleMessage;
	
	private Player player;
	private Enemy enemy;
	
	public Battle( Player player , Enemy enemy ) {
		
		this.player = player;
		this.enemy  = enemy;
		
		this.playerHp = player.getHp();
		this.playerMp = player.getMp();
		this.enemyHp  = enemy.getHp();
		this.enemyMp  = enemy.getMp();
	}
	
	public void damegeCalculationEnemy( Integer damage ) {
		this.enemyHp -= damage;
		battleMessage.add( player.getName() + "の攻撃‼" );
		battleMessage.add( enemy.getName() + "に" + damage + "ダメージ!!!" );
		
		if( enemyHp <= 0 ) {
			this.enemyHp = 0;
			battleMessage.add( enemy.getName() + "を倒した!!!" );
		}
	}
	
	public void startMessage( String enemyName ) {
		battleMessage = new ArrayList<>();
		battleMessage.add( enemyName + "が現れた!!!" );
	}

	public void resetMessage() {
		this.battleMessage.clear();
	}
}
