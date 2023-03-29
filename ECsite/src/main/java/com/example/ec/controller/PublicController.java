package com.example.ec.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.ec.entity.Category;
import com.example.ec.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final CategoryRepository categoryRepository;
	
	
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Category> categoryList = categoryRepository.findAll();
		mv.addObject( "categoryList" , categoryList );
		
		return mv;
	}
	
	
	@GetMapping( "/shop/{id}")
	public ModelAndView shop( @PathVariable( name = "id" ) int id , ModelAndView mv ) {
		
		mv.setViewName( "shop" );
		Category category = categoryRepository.findById( id ).orElseThrow();
		
		mv.addObject( "category" , category );
		
		return mv;
		
	}

}
