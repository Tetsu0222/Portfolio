package com.example.rpg2.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.rpg2.entity.Magic;

import lombok.Data;

@Data
public class Target {
	
	private String  skillName;
	private Integer executionId;
	private String  selectionName;
	private Integer selectionId;
	private String  category;
	private Magic   executionMagic;
	private List<Integer> targetListEnemy = new ArrayList<>();
	
	
	//通常攻撃
	public Target( MonsterData monsterData , Integer myKeys , Integer key ) {
		
		this.skillName     = "通常攻撃";
		this.selectionName = monsterData.getName();
		this.selectionId   = key;
		this.executionId   = myKeys;
		this.category	   = "attack";
		this.targetListEnemy = null;
	}
	
	//味方への魔法
	public Target( AllyData receptionAllyData , Integer myKeys , Integer key , Magic magic ) {
		
		this.skillName      = magic.getName();
		this.selectionName  = receptionAllyData.getName();
		this.selectionId    = key;
		this.executionId    = myKeys;
		this.category	    = magic.getCategory();
		this.executionMagic = magic;
		this.targetListEnemy = null;
	}
	
	//攻撃魔法
	public Target( MonsterData monsterData , Integer myKeys , Integer key , Magic magic ) {
		
		this.skillName      = magic.getName();
		this.selectionName  = monsterData.getName();
		this.selectionId    = key;
		this.executionId    = myKeys;
		this.category	    = magic.getCategory();
		this.executionMagic = magic;
		this.targetListEnemy = null;
	}
	
	//全体攻撃魔法
	public Target( Map<Integer,MonsterData> monsterDataMap , List<Integer> targetListEnemy , Integer myKeys , Magic magic ) {
		
		this.skillName      = magic.getName();
		this.selectionName  = "敵全体";
		this.executionId    = myKeys;
		this.category	    = magic.getCategory();
		this.executionMagic = magic;
		this.targetListEnemy = targetListEnemy;
	}
	
	//防御選択時の処理
	public Target( Integer myKeys , String skillName ) {
		
		this.skillName     = skillName;
		this.selectionName = "";
		this.selectionId   = 0;
		this.executionId   = myKeys;
		this.category	   = "unable";
		this.targetListEnemy = null;
	}
	
	//死亡時
	public Target( Integer myKeys ) {
		
		this.skillName     = "";
		this.selectionName = "";
		this.selectionId   = 0;
		this.executionId   = myKeys;
		this.category	   = "unable";
		this.targetListEnemy = null;
	}

}
