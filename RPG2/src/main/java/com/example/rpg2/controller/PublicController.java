package com.example.rpg2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg2.battle.AllyData;
import com.example.rpg2.entity.Ally;
import com.example.rpg2.repository.AllyRepository;
import com.example.rpg2.repository.MagicRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final MagicRepository magicRepository;
	private final AllyRepository  allyRepository;
	private final HttpSession     session;
	
	
	
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		
		mv.setViewName( "test" );
		
		Ally ally = allyRepository.findById( 1 ).orElseThrow();
		AllyData allyData = new AllyData( ally , magicRepository );
		
		session.setAttribute( "allyData" , allyData );

		
		
		return mv;
	}

}
