package com.example.bakery.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.bakery.entity.Bakery;
import com.example.bakery.form.Basket;
import com.example.bakery.repository.BakeryRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class publicController {
	
	private final BakeryRepository bakeryRepository;
	private final HttpSession      session;
	
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Bakery> bakeryList = bakeryRepository.findAll();
		mv.addObject( "bakeryList", bakeryList );
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
		
		session.setAttribute( "basket" , basket );
		
		return mv;
	}
	
	
	//商品カートに追加
	@GetMapping( "/add/{id}" )
	public String Add( @PathVariable( name = "id" ) int id ,
						Model model ) {
		
		Bakery bakery = bakeryRepository.findById( id ).orElseThrow();
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
			
		basket.addBakery( bakery , bakeryRepository );
		session.setAttribute( "basket" , basket );
		
		return "redirect:/";
	}
	
	
	//商品カートから削除
	@GetMapping("/basket/delete")
	public String delete( @RequestParam ( name = "ba_name" ) String name , 
						  Model model ) {
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		basket.deleteBasket( name );
		session.setAttribute( "basket" , basket );
		
		return "redirect:/";
	}
	

}
