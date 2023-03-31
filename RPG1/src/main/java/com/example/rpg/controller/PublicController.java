package com.example.rpg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg.entity.Enemy;
import com.example.rpg.entity.Player;
import com.example.rpg.form.Battle;
import com.example.rpg.repository.EnemyRepository;
import com.example.rpg.repository.PlayerRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final PlayerRepository playerRepository;
	private final EnemyRepository  enemyRepository;
	private final HttpSession      session;
	
	
	//TOP画面へ遷移
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		
		Player player = playerRepository.findById(1).orElseThrow();
		Enemy enemy	  = enemyRepository.findById(1).orElseThrow();
		Battle battle = new Battle( player , enemy );
		battle.startMessage( enemy.getName() );
		session.setAttribute( "enemy"  , enemy  );
		session.setAttribute( "player" , player );
		session.setAttribute( "battle" , battle );

		
		return mv;
	}
	
	@GetMapping( "/attack" )
	public ModelAndView attack( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		battle.resetMessage();
		
		Integer damage = 10;
		battle.damegeCalculationEnemy( damage );
		
		session.setAttribute( "battle" , battle );
		
		
		return mv;
	}
	
	
}