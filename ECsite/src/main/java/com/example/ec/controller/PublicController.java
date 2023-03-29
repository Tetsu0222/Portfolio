package com.example.ec.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ec.entity.Category;
import com.example.ec.entity.Goods;
import com.example.ec.repository.CategoryRepository;
import com.example.ec.repository.GoodsRepository;
import com.example.ec.service.Basket;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final CategoryRepository categoryRepository;
	private final GoodsRepository    goodsRepository   ;
	private final HttpSession        session;
	
	
	//TOP画面へ遷移
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Goods> goodsList = goodsRepository.findAll();
		List<Category> categoryList = categoryRepository.findAll();
		mv.addObject( "categoryList" , categoryList );
		mv.addObject( "goodsList" , goodsList );
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
		
		session.setAttribute( "basket" , basket );
		
		return mv;
	}
	
	
	//カテゴリー別商品一覧へ遷移
	@GetMapping( "/shop/{id}")
	public ModelAndView shop( @PathVariable( name = "id" ) int id , ModelAndView mv ) {
		
		mv.setViewName( "shop" );
		Category category = categoryRepository.findById( id ).orElseThrow();
		
		List<Category> categoryList = categoryRepository.findAll();
		mv.addObject( "categoryList" , categoryList );
		mv.addObject( "category" , category );
		
		return mv;
		
	}
	
	
	//カートへ商品を追加
	@GetMapping( "/add/{id}" )
	public String add( @PathVariable( name = "id" ) int id  ,
						@RequestParam( name = "quantity" ) Long quantity ,
						Model model ) {
		
		Goods goods = goodsRepository.findById( id ).orElseThrow();
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
			
		basket.addGoods( goods , goodsRepository , quantity );
		session.setAttribute( "basket" , basket );
		
		
		return "redirect:/";
	}

}
