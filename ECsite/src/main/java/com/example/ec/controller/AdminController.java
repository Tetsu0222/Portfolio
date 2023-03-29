package com.example.ec.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.ec.entity.Category;
import com.example.ec.entity.Goods;
import com.example.ec.form.CategoryData;
import com.example.ec.form.GoodsData;
import com.example.ec.repository.CategoryRepository;
import com.example.ec.repository.GoodsRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final CategoryRepository categoryRepository ;
	private final GoodsRepository    goodsRepository    ;
	
	
	//管理画面へ遷移
	@GetMapping( "/admin" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "admin" );
		
		return mv;
	}
	
	
	//商品カテゴリー管理画面へ遷移
	@GetMapping( "/category" )
	public ModelAndView category( ModelAndView mv ) {
		
		mv.setViewName( "category" );
		List<Category> categoryList = categoryRepository.findAll();
		mv.addObject( "categoryList" , categoryList );
		mv.addObject( "categoryData" , new CategoryData() );
		
		return mv;
	}
	
	
	//商品カテゴリーの追加
	@PostMapping( "/category/create" )
	public String categoryCreate( @ModelAttribute CategoryData categoryData , Model model ) {
		
		System.out.println( categoryData.getName() );
		
		Category category = categoryData.toEntity();
		categoryRepository.saveAndFlush( category );
		
		return "redirect:/category";
	}
	
	
	//商品管理画面へ遷移
	@GetMapping( "/goods" )
	public ModelAndView goods( ModelAndView mv ) {
		
		mv.setViewName( "goods" );
		List<Goods> goodsList = goodsRepository.findAll();
		mv.addObject( "goodsList" , goodsList );
		mv.addObject( "goodsData" , new GoodsData() );
		
		List<Category> categoryList = categoryRepository.findAll();
		mv.addObject( "categoryList" , categoryList );
		
		return mv;
	}
	
	
	//商品の追加
	@PostMapping( "/goods/create" )
	public String goodsCreate( @ModelAttribute GoodsData goodsData , Model model ) {
		
		Goods goods = goodsData.toEntity();
		goodsRepository.saveAndFlush( goods );
		
		return "redirect:/goods";
	}
	
	
	//管理画面へ戻る。
	@PostMapping( "/cancel" )
	public ModelAndView cancel( ModelAndView mv ) {
		
		mv.setViewName( "admin" );
		
		return mv;
	}
	
	

}
