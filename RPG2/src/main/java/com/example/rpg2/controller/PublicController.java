package com.example.rpg2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.rpg2.battle.AllyData;
import com.example.rpg2.battle.Battle;
import com.example.rpg2.battle.MonsterData;
import com.example.rpg2.entity.Ally;
import com.example.rpg2.entity.Magic;
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
	
	private Integer keys;
	
	
	
	//TOP画面に対応
	@GetMapping( "/" )
	public ModelAndView Index( ModelAndView mv ) {
		
		mv.setViewName( "index" );
		
		//プレイアブルキャラクターとエネミーキャラクターの選択肢を提示
		List<Ally>    allyList    = allyRepository.findAll();
		List<Monster> monsterList = monsterRepository.findAll();

		mv.addObject( "allyList"    , allyList    );
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
		
		Battle battle = new Battle( partyList , monsterData );
		
		//戦闘画面用のデータをセッションスコープに保存
		session.setAttribute( "battle" , battle );
		
		return mv;
	}
	
	
	//通常攻撃を選択
	@GetMapping( "/attack/{key}" )
	public ModelAndView attack( @PathVariable( name = "key" ) int key ,
								ModelAndView mv ) {
		
		mv.setViewName( "test" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		
		battle.selectionAttack( key );
		
		session.setAttribute( "battle" , battle );
		session.setAttribute( "mode" , "log" );
		
		return mv;
		
	}
	
	
	//魔法選択画面を表示
	@GetMapping( "/magic/{key}" )
	public ModelAndView magic( @PathVariable( name = "key" ) int key ,
								ModelAndView mv ) {
		
		mv.setViewName( "test" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		
		keys = key;
		
		//発動可能な魔法一覧を表示
		List<Magic> magicList = battle.getPartyMap().get( key ).getMagicList();
		mv.addObject( "magicList" , magicList );
		session.setAttribute( "mode" , "magic" );
		
		return mv;
		
	}
	
	
	//魔法を選択
	@GetMapping( "/magic/add/{id}" )
	public ModelAndView magic2( @PathVariable( name = "id" ) int id , 
								ModelAndView mv ) {
		
		mv.setViewName( "test" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		Magic magic   = magicRepository.findById( id ).get();
		battle.selectionMagic( keys , magic );
		
		//編集点1 前半条件を削除予定
		if( !magic.getCategory().equals( "attackmagic" ) && magic.getRange().equals( "single" )) {
			session.setAttribute( "mode" , "targetAllyMagic" );
			
			return mv;
		}
		
		
		session.setAttribute( "battle" , battle );
		session.setAttribute( "mode" , "log" );
		
		return mv;
		
	}
	
	
	//ターゲット選択
	@GetMapping( "/target/magic/ally/{key}" )
	public ModelAndView targetAlly( @PathVariable( name = "key" ) int key ,
									ModelAndView mv ) {
		
		mv.setViewName( "test" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		String name = battle.getPartyMap().get( key ).getName();
		
		battle.getSelectionMap().get( keys ).setTargetId( key );
		battle.getSelectionMap().get( keys ).setTargetName( name );
		
		session.setAttribute( "battle" , battle );
		session.setAttribute( "mode" , "log" );
		
		return mv;
	}
	
	
	//戦闘開始
	@GetMapping( "/start" )
	public ModelAndView start( ModelAndView mv ) {
		
		//いつもの処理
		mv.setViewName( "battle" );
		Battle battle = (Battle)session.getAttribute( "battle" );
		
		
		return mv;
	}
	

}
