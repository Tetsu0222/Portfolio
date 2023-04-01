package com.example.rpg2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg2.battle.AllyData;
import com.example.rpg2.battle.MonsterData;
import com.example.rpg2.entity.Ally;
import com.example.rpg2.entity.Monster;
import com.example.rpg2.repository.AllyRepository;
import com.example.rpg2.repository.MagicRepository;
import com.example.rpg2.repository.MonsterPatternRepository;
import com.example.rpg2.repository.MonsterRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PublicController {
	
	private final MagicRepository 			magicRepository;
	private final AllyRepository  			allyRepository;
	private final MonsterRepository 		monsterRepository;
	private final MonsterPatternRepository  monsterPatternRepository;
	private final HttpSession				session;
	
	
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		List<Ally> allyList = allyRepository.findAll();
		List<Monster> monsterList = monsterRepository.findAll();
		
		mv.addObject( "allyList" , allyList );
		mv.addObject( "monsterList" , monsterList );
		
		session.invalidate();
		
		return mv;
	}
	
	
	//バトルへ遷移
	@GetMapping( "/battle" )
	public ModelAndView battle( @RequestParam( name = "PLV" ) int pid ,
								@RequestParam( name = "MLV" ) int mid ,
								ModelAndView mv ) {
		
		//test → battleへ変更予定
		mv.setViewName( "test" );
		
		//選択に応じたプレイアブルキャラクターを生成
		Ally ally = allyRepository.findById( pid ).orElseThrow();
		AllyData allyData = new AllyData( ally , magicRepository );
		
		//選択に応じたエネミーオブジェクトを生成
		Monster monster = monsterRepository.findById( mid ).orElseThrow();
		MonsterData monsterData = new MonsterData( monster , monsterPatternRepository );
		
		//戦闘画面用のデータをセッションスコープに保存
		session.setAttribute( "allyData"    , allyData    );
		session.setAttribute( "monsterData" , monsterData );
		
		
		return mv;
	}
	

}
