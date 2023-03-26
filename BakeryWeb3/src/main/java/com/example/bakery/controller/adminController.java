package com.example.bakery.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.bakery.entity.Bakery;
import com.example.bakery.form.BakeryData;
import com.example.bakery.repository.BakeryRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class adminController {
	
	
	private final BakeryRepository bakeryRepository;
	private final HttpSession      session;
	
	
	
	//管理画面 商品メニュー追加画面へ遷移
	@GetMapping( "/bakery/create" )
	public ModelAndView create( ModelAndView mv ) {
		
		List<Bakery> bakeryList = bakeryRepository.findAll();
		
		mv.setViewName( "bakery" );
		
		mv.addObject( "bakeryData" , new BakeryData() );
		mv.addObject( "bakeryList" , bakeryList      );
		
		return mv;
	}
	
	
	//管理画面 メニュー追加
	@PostMapping( "/bakery/create" )
	public String create( @ModelAttribute @Validated BakeryData bakeryData ,
						   BindingResult result ) {
		
		if ( !result.hasErrors() ) {
            Bakery bakery = bakeryData.toEntity();
            bakeryRepository.saveAndFlush( bakery );
            return "redirect:/bakery/create";
		}
		
		return "bakery";
	}
	
	
    //管理画面 メニューを削除する
    @GetMapping( "/bakery/delete" )
    public String deleteNews( @RequestParam( name = "ba_id" ) int baId  ) {
    	
        //テーブルから削除
    	bakeryRepository.deleteById( baId );

        return "redirect:/bakery/create";
    }
    
    
    //商品一覧画面へ戻る。
	@PostMapping( "/cancel" )
	public ModelAndView cancel( ModelAndView mv ,
								@PageableDefault( page = 0 , size = 5 , sort = "id" ) Pageable pageable) {
		
		mv.setViewName( "index" );
		
        Pageable prevPageable = (Pageable)session.getAttribute( "prevPageable" );
        if( prevPageable == null ) {
            prevPageable = pageable;
            session.setAttribute( "prevPageable" , prevPageable );
        }
        
		Page<Bakery> pageList = bakeryRepository.findAll( pageable );
		mv.addObject( "pageList" , pageList );
		mv.addObject( "bakeryList", pageList.getContent() );
		
		return mv;
	}

}
