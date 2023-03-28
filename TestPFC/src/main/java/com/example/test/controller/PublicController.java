package com.example.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.test.entity.Pfc;
import com.example.test.repository.PfcRepository;
import com.example.test.service.Total;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	
	private final PfcRepository pfcRepository;
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Pfc> pfcList = pfcRepository.findAll();
		
		//合計値を算出
		Total total = new Total( pfcList );
		
		//各項目を表示画面へ
		mv.addObject( "pfcList" , pfcList );
		
		//合計値を表示画面へ
		mv.addObject( "total" , total );
		
		
		return mv;
	}
	

}
