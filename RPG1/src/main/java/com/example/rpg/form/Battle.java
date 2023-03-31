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
	
	
	public Battle( Player player , Enemy enemy ) {
		
		this.playerHp = player.getHp();
		this.playerMp = player.getMp();
		this.enemyHp  = enemy.getHp();
		this.enemyMp  = enemy.getMp();
	}
	
	public void damegeCalculationEnemy( Integer damage ) {
		
		this.enemyHp -= damage;
		
		if( enemyHp < 0 ) {
			this.enemyHp = 0;
		}
	}
	
	public void startMessage( String enemyName ) {
		battleMessage = new ArrayList<>();
		battleMessage.add( enemyName + "が現れた!!!" );
	}

}
