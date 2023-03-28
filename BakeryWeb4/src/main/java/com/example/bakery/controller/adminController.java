package com.example.bakery.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.bakery.entity.Bakery;
import com.example.bakery.entity.Customer;
import com.example.bakery.entity.OrderBakeryTest;
import com.example.bakery.form.BakeryData;
import com.example.bakery.form.CustomerData;
import com.example.bakery.repository.BakeryRepository;
import com.example.bakery.repository.CustomerRepository;
import com.example.bakery.repository.OrderBakeryTestRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class adminController {
	
	private final BakeryRepository bakeryRepository;
	private final CustomerRepository customerRepository;
	private final OrderBakeryTestRepository orderBakeryTestRepository;
	
	//管理画面へ遷移
	@GetMapping( "/management" )
	public ModelAndView management( ModelAndView mv ) {
		
		mv.setViewName( "management" );
		
		return mv;
	}
	
	
	//商品メニュー画面へ遷移
	@GetMapping( "/bakery/create" )
	public ModelAndView create( ModelAndView mv ) {
		
		List<Bakery> bakeryList = bakeryRepository.findAll();
		
		mv.setViewName( "bakery" );
		
		mv.addObject( "bakeryData" , new BakeryData() );
		mv.addObject( "bakeryList" , bakeryList      );
		
		return mv;
	}
	
	
	//メニュー追加
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
	
	
    //メニュー削除
    @GetMapping( "/bakery/delete" )
    public String deleteNews( @RequestParam( name = "ba_id" ) int baId  ) {
    	
        //テーブルから削除
    	bakeryRepository.deleteById( baId );

        return "redirect:/bakery/create";
    }
    
    
    //顧客一覧画面（テスト）へ遷移
    @GetMapping( "/test" )
    public ModelAndView test( ModelAndView mv ) {
    	
    	mv.setViewName( "test" );
    	List<OrderBakeryTest> testList = orderBakeryTestRepository.findAll();
    	mv.addObject( "testList" , testList );
    	
    	return mv;
    }
    
    
    //顧客一覧画面へ遷移
    @GetMapping( "/customer" )
    public ModelAndView customer( ModelAndView mv ) {
    	
    	mv.setViewName( "customer" );
    	List<Customer> customerList = customerRepository.findAll();
    	mv.addObject( "customerList" , customerList );
    	
    	return mv;
    }
    
    
    //顧客毎の注文状況確認画面へ遷移
    @GetMapping( "/order/{id}")
    public ModelAndView orderBakery( @PathVariable( name = "id" ) int id ,
    								 ModelAndView mv ) {
    	
    	mv.setViewName( "order" );
    	Customer customer = customerRepository.findById( id ).get();
    	mv.addObject( "customerData" ,  new CustomerData( customer ) );
    	
    	return mv;
    }
    
    
    //管理画面へ戻る。
	@PostMapping( "/cancel" )
	public ModelAndView cancel( ModelAndView mv ) {
		
		mv.setViewName( "management" );
		
		return mv;
	}

}
