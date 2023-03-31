package com.example.rpg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg.entity.Enemy;
import com.example.rpg.entity.Player;
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
		
		session.setAttribute( "enemy"  , enemy  );
		session.setAttribute( "player" , player );
		
		/*
		mv.addObject( "enemy"  , enemy  );
		mv.addObject( "player" , player );
		*/
		
		return mv;
	}
	
}