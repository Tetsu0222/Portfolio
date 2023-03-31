package com.example.rpg.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.example.rpg.entity.Enemy;
import com.example.rpg.entity.Magic;
import com.example.rpg.entity.Player;
import com.example.rpg.repository.MagicRepository;

import jakarta.servlet.http.HttpSession;
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
	private List<Magic> magicList;
	
	private Player player;
	private Enemy enemy;
	
	
	//コンストラクタ
	public Battle( Player player , Enemy enemy , MagicRepository  magicRepository ) {
		
		this.player = player;
		this.enemy  = enemy;
		
		this.playerHp  = player.getHp();
		this.playerMp  = player.getMp();
		this.playerATK = player.getAtk();
		this.playerDEF = player.getDef();
		
		this.magicList = new ArrayList<>();
		IntStream.rangeClosed( 1 , player.getLv() )
		.forEach( s -> magicList.add( magicRepository.findById( s ).orElseThrow() ));
		
		this.enemyHp  = enemy.getHp();
		this.enemyMp  = enemy.getMp();
		this.enemyATK = enemy.getAtk();
		this.enemyDEF = enemy.getDef();
	}
	
	
	//ダメージを受ける計算
	public void damegeCalculationPlayer( Integer damage ) {
		
		Random random = new Random();
		damage += random.nextInt( 10 ) - playerDEF;
		
		if( damage < 0 ) {
			battleMessage.add( enemy.getName() + "の攻撃‼" );
			battleMessage.add( player.getName() + "はダメージを受けない!" );
			
		}else{
			this.playerHp -= damage;
			battleMessage.add( enemy.getName() + "の攻撃‼" );
			battleMessage.add( player.getName() + "は" + damage + "のダメージを受けた!!" );
		}
		

		
		if( playerHp <= 0 ) {
			this.playerHp = 0;
			battleMessage.add( player.getName() + "は負けてしまった…" );
		}
	}
	
	
	//回復する計算
	public void recovery( Integer recovery , String magicName , Integer magicMp ) {
		
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
	
	
	//補助魔法の計算1
	public void buff( Integer buff , String magicName , Integer magicMp ) {
		
		if( playerMp - magicMp > 0 ) {
			this.playerDEF += buff;
			battleMessage.add( player.getName() + "は" + magicName + "を発動した!!!" );
			battleMessage.add( player.getName() + "の肉体は岩のように硬くなる!!" );
			
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
		
		if( perpetrator < 0 ) {
			battleMessage.add( player.getName() + "の攻撃‼" );
			battleMessage.add( enemy.getName() + "にダメージを与えられない!" );
		}
		
		this.enemyHp -= perpetrator;
		battleMessage.add( player.getName() + "の攻撃‼" );
		battleMessage.add( enemy.getName() + "に" + perpetrator + "のダメージ!!!" );
		
		if( enemyHp <= 0 ) {
			this.enemyHp = 0;
			battleMessage.add( enemy.getName() + "を倒した!!!" );
		}
	}
	
	
	//ダメージを与える計算（魔法攻撃）
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
	
	
	//妨害魔法の計算1
	public void debuff( Integer debuff , String magicName , Integer magicMp ) {
		
		if( playerMp - magicMp > 0 ) {
			this.enemyDEF -= debuff;
			battleMessage.add( player.getName() + "は" + magicName + "を発動した!!!" );
			battleMessage.add( enemy.getName() + "の気持ち悪さが下がった!!" );
			
			this.playerMp -= magicMp;
			
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
	
	
	//魔法の発動処理 処理を魔法のカテゴリーに応じて分岐
	public void magicCalculation( Magic magic ) {
		
		//攻撃魔法
		if( magic.getCategory().equals( "attackmagic" )) {
			this.damegeCalculationEnemy( magic.getPoint() , magic.getName() , magic.getMp() );
		}
		
		//回復魔法
		if( magic.getCategory().equals( "recoverymagic" )) {
			this.recovery( magic.getPoint() , magic.getName() , magic.getMp() );
		}
		
		//補助魔法1（バフ）
		if( magic.getCategory().equals( "buffmagic" )) {
			this.buff( magic.getPoint() , magic.getName() , magic.getMp() );
		}
		
		//補助魔法1（デバフ）
		if( magic.getCategory().equals( "debuffmagic" )) {
			this.debuff( magic.getPoint() , magic.getName() , magic.getMp() );
		}
		
	}
	
	
	//戦闘継続チェック
	public void idFinishBattle( HttpSession session ) {
		
		if( this.getEnemyHp() > 0 && this.getPlayerHp() > 0 ) {
			session.setAttribute( "battle" , this );
			session.setAttribute( "mode" , "log" );
			
		}else{
			session.setAttribute( "battle" , this );
			session.setAttribute( "mode" , "result" );
		}
	}
	
}
