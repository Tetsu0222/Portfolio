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
	
	
	//カートへ商品を追加(カテゴリーから）
	@GetMapping( "/add/shop/{id}" )
	public String addInCategory( @PathVariable( name = "id" ) int id  ,
								@RequestParam( name = "quantity" ) Long quantity ,
								Model model ) {
		
		Goods goods = goodsRepository.findById( id ).orElseThrow();
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
			
		basket.addGoods( goods , goodsRepository , quantity );
		session.setAttribute( "basket" , basket );
		
		
		return "redirect:/shop/" + goods.getCategory().getId();
	}
	
	
	//カートの中身へ遷移
	@GetMapping( "/basket")
	public ModelAndView basket( ModelAndView mv ) {
	
		mv.setViewName( "basket" );
		
		return mv;
	}
	
	
	//カート内の商品の数量を変更
	@GetMapping( "/change/{name}" )
	public String change( @PathVariable ( name = "name" ) String changeName , 
						  @RequestParam ( name = "quantity" ) Long quantity ) {
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		basket.changeBasket( changeName , quantity );
		session.setAttribute( "basket" , basket );
		
		return "redirect:/basket";
	}
	
	
	//商品カートから削除
	@GetMapping("/basket/delete")
	public String delete( @RequestParam ( name = "ba_name" ) String name , 
						  Model model ) {
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		basket.deleteBasket( name );
		session.setAttribute( "basket" , basket );
		
		return "redirect:/basket";
	}

}
