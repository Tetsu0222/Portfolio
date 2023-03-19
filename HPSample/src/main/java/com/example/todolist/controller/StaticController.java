package com.example.todolist.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todolist.entity.Comment;
import com.example.todolist.form.CommentData;
import com.example.todolist.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StaticController {
	
	private final CommentRepository commentRepository;


	//TOP画面に対応
	@GetMapping( "/" )
	@PreAuthorize("permitAll")
	public ModelAndView Index( ModelAndView mv ) {
			mv.setViewName( "index" );
			return mv;
	}
	
	
	//TOP画面に対応
	@GetMapping( "/menu" )
	@PreAuthorize("permitAll")
	public ModelAndView menu( ModelAndView mv ) {
			mv.setViewName( "menu" );
			return mv;
	}
	
	
	//TOP画面に対応
	@GetMapping( "/news" )
	@PreAuthorize("permitAll")
	public ModelAndView news( ModelAndView mv ) {
			mv.setViewName( "news" );
			return mv;
	}
	
	
	//TOP画面に対応
	@GetMapping( "/contact" )
	@PreAuthorize("permitAll")
	public ModelAndView contact( ModelAndView mv ) {
			mv.setViewName( "contact" );
			mv.addObject( "commentData", new CommentData() );
			return mv;
	}
	
	
    //コメントの登録（ゲスト操作）
	@PostMapping( "/comment/create" )
	@PreAuthorize("permitAll")
	public String createComment( @ModelAttribute @Validated CommentData commentData , Model model ) {
		Comment com = commentData.toEntity();
		commentRepository.saveAndFlush( com );
		return "redirect:/contact";
	}
	
	
}
