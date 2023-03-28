package com.example.bakery.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bakery.dao.BakeryDaoImp;
import com.example.bakery.entity.Bakery;
import com.example.bakery.entity.OrderBakeryTest;
import com.example.bakery.form.BakeryQuery;
import com.example.bakery.form.Basket;
import com.example.bakery.form.OrderBakeryTestData;
import com.example.bakery.repository.BakeryRepository;
import com.example.bakery.repository.OrderBakeryTestRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class publicController {
	
	private final BakeryRepository bakeryRepository;
	private final HttpSession      session;
	private final OrderBakeryTestRepository orderBakeryTestRepository;
	
    @PersistenceContext
    private EntityManager entityManager;
    BakeryDaoImp bakeryDaoImp;
	
    @PostConstruct
    public void init() {
    	bakeryDaoImp = new BakeryDaoImp( entityManager );
    }
    
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Bakery> bakeryList = bakeryRepository.findAll();
		mv.addObject( "bakeryList", bakeryList );
		mv.addObject( "bakeryQuery" , new BakeryQuery() );
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
		
		session.setAttribute( "basket" , basket );
		
		return mv;
	}
	
	
	//商品検索に対応
	@PostMapping( "/query" )
	public ModelAndView Query( @ModelAttribute @Validated BakeryQuery bakeryQuery ,
							   BindingResult result ,
							   ModelAndView mv ,
							   RedirectAttributes redirectAttributes ) {
		
		if( !result.hasErrors() ) {
			mv.setViewName( "index" );
			List<Bakery> bakeryList = bakeryDaoImp.findByCriteria( bakeryQuery );
			
			mv.addObject( "bakeryList" , bakeryList );
			mv.addObject( "bakeryQuery" , new BakeryQuery() );
			
		}else{
			mv.setViewName( "index" );
			List<Bakery> bakeryList = bakeryRepository.findAll();
			mv.addObject( "bakeryList", bakeryList );
			mv.addObject( "bakeryQuery" , new BakeryQuery() );
		}
		
		return mv;
	}
	
	
	//商品カートに追加
	@GetMapping( "/add/{id}" )
	public String Add( @PathVariable( name = "id" ) int id ,
					   @RequestParam( name = "quantity" ) Long quantity ,
					   Model model ) {
		
		Bakery bakery = bakeryRepository.findById( id ).orElseThrow();
		Basket basket = (Basket)session.getAttribute( "basket" );
		
		if( basket == null ) {
			basket = new Basket();
		}
			
		basket.addBakery( bakery , bakeryRepository , quantity );
		session.setAttribute( "basket" , basket );
		
		return "redirect:/";
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
	
	
	//カートの中身へ遷移
	@GetMapping( "/basket")
	public ModelAndView basket( ModelAndView mv ) {
	
		mv.setViewName( "basket" );
		
		return mv;
	}
	
	
	//会計処理画面へ遷移
	@GetMapping( "/account" )
	public ModelAndView account( ModelAndView mv ) {
		
		mv.setViewName( "account" );
		mv.addObject( "orderBakeryTestData" , new OrderBakeryTestData() );
		
		return mv;
	}
	
	
	//注文登録
	@PostMapping( "/account" )
	public String accountOrder( @ModelAttribute OrderBakeryTestData orderBakeryTestData ,
									  Model model ) {
		
		Basket basket = (Basket)session.getAttribute( "basket" );
		long total = basket.getTotalPrice();
		int totalprice = Math.toIntExact( total );
		String goods = basket.getQuantityMap().toString();
		OrderBakeryTest orderBakeryTest = orderBakeryTestData.toEntity( orderBakeryTestData , totalprice , goods );
		orderBakeryTestRepository.saveAndFlush( orderBakeryTest );
		session.invalidate();
		
		return "redirect:/";
	}
	

}
