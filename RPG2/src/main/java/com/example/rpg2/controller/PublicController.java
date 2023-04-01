package com.example.rpg2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg2.battle.AllyData;
import com.example.rpg2.battle.MonsterData;
import com.example.rpg2.entity.Ally;
import com.example.rpg2.entity.Monster;
import com.example.rpg2.repository.AllyRepository;
import com.example.rpg2.repository.MagicRepository;
import com.example.rpg2.repository.MonsterRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final MagicRepository magicRepository;
	private final AllyRepository  allyRepository;
	private final MonsterRepository monsterRepository;
	private final HttpSession     session;
	
	
	
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		
		mv.setViewName( "test" );
		
		Ally ally = allyRepository.findById( 1 ).orElseThrow();
		AllyData allyData = new AllyData( ally , magicRepository );
		Monster monster = monsterRepository.findById( 1 ).orElseThrow();
		MonsterData monsterData = new MonsterData( monster );
		
		session.setAttribute( "allyData" , allyData );
		session.setAttribute( "monsterData" , monsterData );
		
		
		return mv;
	}

}
