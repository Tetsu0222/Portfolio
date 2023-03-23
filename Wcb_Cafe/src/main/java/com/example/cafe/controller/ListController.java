package com.example.cafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cafe.entity.Content;
import com.example.cafe.entity.News;
import com.example.cafe.form.NewsData;
import com.example.cafe.repository.ContentRepository;
import com.example.cafe.repository.NewsRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListController {
	
	private final NewsRepository    newsRepository;
	private final ContentRepository contentRepository;
	private final HttpSession       session;

	
	//管理画面のTOPを表示
	@GetMapping( "/admin" )
	public ModelAndView admin( ModelAndView mv ) {
		
		mv.setViewName( "admin" );
		List<News> newsList = newsRepository.findAll();
		mv.addObject( "newsList" , newsList );
		
		return mv;
		
	}
	
	
	//TOP画面から新規登録（カテゴリー）を押下
	@PostMapping( "/news/create/form" )
	public ModelAndView newsCreate( ModelAndView mv ) {
		
        mv.setViewName( "list" );
        mv.addObject  ( "newsData" , new NewsData() );
        session.setAttribute( "mode" , "create" );
		
		return mv;
	}
	
	
	//カテゴリー別の詳細画面を表示
	@GetMapping( "/news/{id}")
	public ModelAndView newsById( @PathVariable( name = "id" ) int id , ModelAndView mv ) {
		
        mv.setViewName( "list" );
        
        //id検索したNewsオブジェクトを生成
        News news = newsRepository.findById( id ).orElseThrow();
        
        mv.addObject( "newsData" , new NewsData( news ) );
        session.setAttribute( "mode" , "update" );
		
		return mv;
	}
	
	
	//カテゴリー新規登録画面で登録を押下
	@PostMapping( "news/create/do" )
	public String newsCreate( @ModelAttribute NewsData newsData , RedirectAttributes redirectAttributes ) {
		
        News news = newsData.toEntity();
        newsRepository.saveAndFlush( news );
        redirectAttributes.addFlashAttribute( "msg" , "登録完了" );
		
		return "redirect:/news/" + news.getId();
	}
	
	
	//カテゴリーの変更
	@PostMapping( "/news/update" )
	public String newsUpdate( @ModelAttribute NewsData newsData , RedirectAttributes redirectAttributes ) {
		
		News news = newsData.toEntity();
		newsRepository.saveAndFlush( news );
		redirectAttributes.addFlashAttribute( "msg" , "変更完了" );
		
		return "redirect:/news/" + news.getId();
	}
	
	
	//コンテンツの新規登録
    @PostMapping( "/content/create" )
    public String createContent( @ModelAttribute NewsData newsData , RedirectAttributes redirectAttributes ) {

        News news   = newsData.toEntity();
        Content con = newsData.toContentEntity();
        con.setNews( news );
        
        contentRepository.saveAndFlush( con );
        
        redirectAttributes.addFlashAttribute( "msg" , "登録完了" );
            
        return "redirect:/news/" + news.getId();
        
    }
	

	//カテゴリーの削除
	@PostMapping( "news/delete" )
	public String newsDelete( @ModelAttribute NewsData newsData , RedirectAttributes redirectAttributes ) {
		
		Integer id = newsData.getId();
		newsRepository.deleteById( id );
		redirectAttributes.addFlashAttribute( "msg" , "削除完了" );
		
		return "redirect:/list";
	}
	
	
    //コンテンツ削除処理
    @GetMapping( "/content/delete" )
    public String deleteTask( @RequestParam( name = "content_id" ) int contentId ,
                              @RequestParam( name = "news_id" ) int newsId , 
                              RedirectAttributes redirectAttributes ) {
    	
        //コンテンツを削除
        contentRepository.deleteById( contentId );
        //削除完了メッセージをセットしてリダイレクト
        redirectAttributes.addFlashAttribute( "msg" , "削除完了" );
        
        return "redirect:/news/" + newsId;
    }
	
	
}
