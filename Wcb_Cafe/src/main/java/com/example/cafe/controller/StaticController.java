package com.example.cafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.cafe.entity.News;
import com.example.cafe.repository.ContentRepository;
import com.example.cafe.repository.NewsRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StaticController {
	
	private final NewsRepository newsRepository;
	private final ContentRepository contentRepository;

	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		mv.setViewName( "index" );
		return mv;
	}
	
	
	//TOP画面に対応
	@GetMapping( "/menu" )
	public ModelAndView menu( ModelAndView mv ) {
		mv.setViewName( "menu" );
		return mv;
	}
	
	
	//TOP画面に対応
	@GetMapping( "/news" )
	public ModelAndView news( ModelAndView mv ) {
		
		mv.setViewName( "news" );
		List<News> newsList = newsRepository.findAll();
		mv.addObject( "newsList" , newsList );
		
	    return mv;
	    
	}
	
	
	//TOP画面に対応
	@GetMapping( "/contact" )
	public ModelAndView contact( ModelAndView mv ) {
		
		mv.setViewName( "contact" );
		//mv.addObject( "commentData", new CommentData() );
		
		return mv;
	}
	
	
	
}
