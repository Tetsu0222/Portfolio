package com.example.rpg.form;

import java.util.Random;

import lombok.Data;

@Data
public class EnemyActions {
	
	
	//行動パターンを決定
	public void enemyAction( Battle battle ) {
		
		Random random = new Random();
		
		int pattern = random.nextInt( battle.getEnemy().getPattern() );
		
		if( pattern == 0 ) {
			battle.damegeCalculationPlayer( battle.getEnemyATK() );
			
		}else if( pattern == 1 ){
			battle.damegeCalculationPlayer( battle.getEnemyATK() );
			
		}else if( pattern == 2 ){
			battle.damegeCalculationPlayer( battle.getEnemyATK() );
			
		}else if( pattern == 3 ){
			battle.moroAT(  battle.getEnemyATK() );
			
		}else if( pattern == 4 ){
			battle.damegeCalculationPlayer( 0 );
			
		}else if( pattern == 5 ){
			battle.reset();
		
		}else if( pattern == 6 ){
			battle.beam( battle.getEnemyATK() );
			
		}else if( pattern == 7 ){
			battle.damegeCalculationPlayer( battle.getEnemyATK() );
		}

	}

}
