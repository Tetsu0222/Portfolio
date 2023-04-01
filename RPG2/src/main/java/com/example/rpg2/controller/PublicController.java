package com.example.rpg2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
	public ModelAndView battle( @RequestParam( name = "PLV1" ) Integer pid1 ,
								@RequestParam( name = "PLV2" ) Integer pid2 ,
								@RequestParam( name = "PLV3" ) Integer pid3 ,
								@RequestParam( name = "PLV4" ) Integer pid4 ,
								@RequestParam( name = "MLV" ) Integer mid ,
								ModelAndView mv ) {
		
		//test → battleへ変更予定
		mv.setViewName( "test" );
		
		//選択に応じたプレイアブルキャラクターを生成
		List<AllyData> partyList = new ArrayList<>();
		
		Stream.of( pid1 , pid2 ,pid3 , pid4 )
		.filter( s -> s > 0 )
		.map( s -> allyRepository.findById( s ).orElseThrow() )
		.map( s -> new AllyData( s , magicRepository ))
		.forEach( s -> partyList.add( s ) );
		
		//選択に応じたエネミーオブジェクトを生成
		Monster monster = monsterRepository.findById( mid ).orElseThrow();
		MonsterData monsterData = new MonsterData( monster , monsterPatternRepository );
		
		//戦闘画面用のデータをセッションスコープに保存
		session.setAttribute( "partyList"    , partyList  );
		session.setAttribute( "monsterData" , monsterData );
		
		
		return mv;
	}
	

}
