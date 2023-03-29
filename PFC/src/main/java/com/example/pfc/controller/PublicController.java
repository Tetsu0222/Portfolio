package com.example.pfc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.pfc.entity.Food;
import com.example.pfc.entity.Menu;
import com.example.pfc.form.MenuData;
import com.example.pfc.repository.FoodRepository;
import com.example.pfc.repository.MenuRepository;
import com.example.pfc.service.Total;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	
	private final FoodRepository foodRepository;
	private final HttpSession    session;
	private final MenuRepository menuRepository;
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Food> foodList = foodRepository.findAll();
		
		//合計値を算出
		Total total = new Total( foodList );
		
		//各項目を表示画面へ
		mv.addObject( "foodList" , foodList );
		
		//合計値を表示画面へ
		mv.addObject( "total" , total );
		
		return mv;
	}
	
	
	//menuを一時登録
	@GetMapping( "/menu/add/{id}" )
	public String addMenu( @PathVariable( name = "id" ) int id ,
							@RequestParam( name = "gram" ) Integer gram ,
							Model model ) {
		
		Food food = foodRepository.findById( id ).orElseThrow();
		MenuData menuData = (MenuData)session.getAttribute( "menuData" );
		
		if( menuData == null ) {
			menuData = new MenuData();
		}
			
		menuData.addFood( food , gram );
		session.setAttribute( "menuData" , menuData );
		
		return "redirect:/";
		
	}
	
	
	//menu登録画面へ
	@GetMapping( "/menu/create" )
	public ModelAndView createMenu( ModelAndView mv ) {
		
		mv.setViewName( "menu" );
		List<Menu> menuList = menuRepository.findAll();
		MenuData menuData = (MenuData)session.getAttribute( "menuData" );
		
		mv.addObject( "menuData" , menuData );
		mv.addObject( "menuList" , menuList );
		
		return mv;
	}
	
	
	@PostMapping( "/menu/create" )
	public String createMenu( @RequestParam( name = "name" ) String name ,
								Model model ) {
		
		MenuData menuData = (MenuData)session.getAttribute( "menuData" );
		menuData.setName( name );
		Menu menu = menuData.toEntity();
		menuRepository.saveAndFlush( menu );
		session.invalidate();
		
		return "redirect:/menu/create";
	}
	

}
