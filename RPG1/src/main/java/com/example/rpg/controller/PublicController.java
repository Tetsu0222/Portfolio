package com.example.rpg.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	//Battle画面へ遷移
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		
		Player player = playerRepository.findById(2).orElseThrow();
		Enemy enemy	  = enemyRepository.findById(2).orElseThrow();
		Battle battle = new Battle( player , enemy );
		battle.startMessage( enemy.getName() );
		session.setAttribute( "enemy"  , enemy  );
		session.setAttribute( "player" , player );
		session.setAttribute( "battle" , battle );

		
		return mv;
	}
	
	
	//通常攻撃を選択
	@GetMapping( "/attack" )
	public ModelAndView attack( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		battle.resetMessage();
		
		Player player = (Player)session.getAttribute( "player" );
		Integer perpetrator = player.getAtk();
		battle.damegeCalculationEnemy( perpetrator );
		
		Enemy enemy	= (Enemy)session.getAttribute( "enemy" );
		Integer damage = enemy.getAtk();
		battle.damegeCalculationPlayer( damage );
		
		session.setAttribute( "battle" , battle );
		session.setAttribute( "mode" , "log" );
		
		return mv;
	}
	
	
	//魔法選択画面を表示
	@GetMapping( "/magic" )
	public ModelAndView magic( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Magic> magicList = magicRepository.findAll();
		
		mv.addObject( "magicList" , magicList );
		session.setAttribute( "mode" , "magic" );
		
		return mv;
		
	}
	
	
	//魔法発動
	@GetMapping( "/magic/{id}" )
	public ModelAndView magicActivation( @PathVariable( name = "id" ) int id ,
										 ModelAndView mv ) {
		
		mv.setViewName( "index" );
		Magic magic = magicRepository.findById( id ).orElseThrow();
		Battle battle = (Battle)session.getAttribute( "battle" );
		battle.resetMessage();
		
		Integer perpetrator = magic.getAtk();
		if( perpetrator > 0 ){
			battle.damegeCalculationEnemy( perpetrator , magic.getName() , magic.getMp() );
		}
		
		Integer recovery = magic.getRecovery();
		if( recovery > 0 ){
			battle.recovery( recovery , magic.getName() , magic.getMp() );
		}
		
		Enemy enemy	= (Enemy)session.getAttribute( "enemy" );
		Integer damage = enemy.getAtk();
		battle.damegeCalculationPlayer( damage );

		session.setAttribute( "mode" , "log" );
		session.setAttribute( "battle" , battle );
		
		return mv;
		
	}
}