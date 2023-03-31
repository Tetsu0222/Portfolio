package com.example.rpg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg.entity.Enemy;
import com.example.rpg.entity.Magic;
import com.example.rpg.entity.Player;
import com.example.rpg.form.Battle;
import com.example.rpg.repository.EnemyRepository;
import com.example.rpg.repository.MagicRepository;
import com.example.rpg.repository.PlayerRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final PlayerRepository playerRepository;
	private final EnemyRepository  enemyRepository;
	private final HttpSession      session;
	private final MagicRepository  magicRepository;
	
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		session.invalidate();
		
		return mv;
	}
	
	
	//Battle画面へ遷移
	@GetMapping( "/battle" )
	public ModelAndView battle( @RequestParam( name = "LV" ) int id ,
								ModelAndView mv ) {
		
		mv.setViewName( "battle" );
		
		//難度に応じたプレイヤーとエネミーを生成
		Player player = playerRepository.findById(id).orElseThrow();
		Enemy enemy	  = enemyRepository.findById(id).orElseThrow();
		Battle battle = new Battle( player , enemy , magicRepository );
		
		//戦闘開始のメッセージを表示
		battle.startMessage( enemy.getName() );
		
		
		session.setAttribute( "enemy"  , enemy  );
		session.setAttribute( "player" , player );
		session.setAttribute( "battle" , battle );
		
		return mv;
	}
	
	
	//通常攻撃を選択
	@GetMapping( "/attack" )
	public ModelAndView attack( ModelAndView mv ) {
		
		//いつもの処理
		mv.setViewName( "battle" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		battle.resetMessage();
		
		//プレイヤーの攻撃処理
		Integer perpetrator = battle.getPlayerATK();
		battle.damegeCalculationEnemy( perpetrator );
		
		//敵の行動処理
		Integer damage = battle.getEnemyATK();
		battle.damegeCalculationPlayer( damage );
		
		//戦闘終了の有無をチェック
		battle.idFinishBattle( session );
		
		return mv;
	}
	
	
	//魔法選択画面を表示
	@GetMapping( "/magic" )
	public ModelAndView magic( ModelAndView mv ) {
		
		//いつもの
		mv.setViewName( "battle" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		
		//発動可能な魔法一覧を表示
		mv.addObject( "magicList" , battle.getMagicList() );
		session.setAttribute( "mode" , "magic" );
		
		return mv;
		
	}
	
	
	//魔法発動
	@GetMapping( "/magic/{id}" )
	public ModelAndView magicActivation( @PathVariable( name = "id" ) int id ,
										 ModelAndView mv ) {
		
		//いつもの処理
		mv.setViewName( "battle" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		battle.resetMessage();
		
		//選択された魔法をDBから生成
		Magic magic = magicRepository.findById( id ).orElseThrow();
		
		//魔法の計算処理
		battle.magicCalculation( magic );

		//敵の行動処理
		Integer damage = battle.getEnemyATK();
		battle.damegeCalculationPlayer( damage );

		//戦闘終了の有無をチェック
		battle.idFinishBattle( session );
		
		return mv;
		
	}
}