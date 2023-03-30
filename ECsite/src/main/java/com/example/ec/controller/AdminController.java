package com.example.ec.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ec.dao.GoodsDaoImpl;
import com.example.ec.entity.Category;
import com.example.ec.entity.Goods;
import com.example.ec.form.CategoryData;
import com.example.ec.form.GoodsData;
import com.example.ec.form.GoodsQuery;
import com.example.ec.repository.CategoryRepository;
import com.example.ec.repository.GoodsRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final CategoryRepository categoryRepository ;
	private final GoodsRepository    goodsRepository    ;
	
    @PersistenceContext
    private EntityManager entityManager;
    GoodsDaoImpl goodsDaoImpl;
	
    @PostConstruct
    public void init() {
    	goodsDaoImpl = new GoodsDaoImpl( entityManager );
    }
	
	
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
	@Transactional
	@PostMapping( "/category/create" )
	public String categoryCreate( @ModelAttribute @Validated CategoryData categoryData ,
									BindingResult result ,
									Model model ) {
		
		if( !result.hasErrors() ) {
			Category category = categoryData.toEntity();
			categoryRepository.saveAndFlush( category );
			
			return "redirect:/category";
			
		}else{

			return "category";
		}
	}
	
	
    //商品カテゴリーの削除
	@Transactional
    @GetMapping( "/category/delete" )
    public String categoryDelete( @RequestParam( name = "ca_id" ) int caId  ) {
    	
        //テーブルから削除
    	categoryRepository.deleteById( caId );

        return "redirect:/category";
    }
	
	
	//商品管理画面へ遷移
	@GetMapping( "/goods" )
	public ModelAndView goods( ModelAndView mv ) {
		
		mv.setViewName( "goods" );
		List<Goods> goodsList = goodsRepository.findAll();
		mv.addObject( "goodsList" , goodsList );
		mv.addObject( "goodsData" , new GoodsData() );
		mv.addObject( "goodsQuery" , new GoodsQuery() );
		
		List<Category> categoryList = categoryRepository.findAll();
		mv.addObject( "categoryList" , categoryList );
		
		return mv;
	}
	
	
	//商品の追加
	@Transactional
	@PostMapping( "/goods/create" )
	public String goodsCreate( @ModelAttribute @Validated GoodsData goodsData ,
								BindingResult result,
								Model model ) {
		
		if( !result .hasErrors() ) {
			Goods goods = goodsData.toEntity();
			goodsRepository.saveAndFlush( goods );
		}
		
		return "redirect:/goods";
	}
	
	
    //商品の削除
	@Transactional
    @GetMapping( "/goods/delete" )
    public String goodsDelete( @RequestParam( name = "go_id" ) int goId  ) {
    	
        //テーブルから削除
    	goodsRepository.deleteById( goId );

        return "redirect:/goods";
    }
	
	
	//商品検索に対応
	@PostMapping("/goods/query")
	public ModelAndView query( @ModelAttribute @Validated GoodsQuery goodsQuery ,
								 BindingResult result ,
								 ModelAndView mv ) {
		
		mv.setViewName( "goods" );
		
		if( !result.hasErrors() ) {
			List<Goods> goodsList = goodsDaoImpl.findByCriteria( goodsQuery );
			
			mv.addObject( "goodsList" , goodsList );
			List<Category> categoryList = categoryRepository.findAll();
			mv.addObject( "categoryList" , categoryList );
			mv.addObject( "goodsData" , new GoodsData() );
			mv.addObject( "goodsQuery" , new GoodsQuery() );
			
		}else{
			List<Goods> goodsList = goodsRepository.findAll();
			List<Category> categoryList = categoryRepository.findAll();
			mv.addObject( "categoryList" , categoryList );
			mv.addObject( "goodsList" , goodsList );
			mv.addObject( "goodsData" , new GoodsData() );
			mv.addObject( "goodsQuery" , new GoodsQuery() );
		}
		
		return mv;
	}
	
	
	//管理画面へ戻る。
	@PostMapping( "/cancel" )
	public ModelAndView cancel( ModelAndView mv ) {
		
		mv.setViewName( "admin" );
		
		return mv;
	}
	
	

}
