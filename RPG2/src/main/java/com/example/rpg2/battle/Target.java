package com.example.rpg2.battle;

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
	
	
	//通常攻撃
	public Target( MonsterData monsterData , Integer myKeys , Integer key ) {
		
		this.skillName     = "通常攻撃";
		this.selectionName = monsterData.getName();
		this.selectionId   = key;
		this.executionId   = myKeys;
		this.category	   = "attack";
	}
	
	//味方への魔法
	public Target( AllyData receptionAllyData , Integer myKeys , Integer key , Magic magic ) {
		
		this.skillName      = magic.getName();
		this.selectionName  = receptionAllyData.getName();
		this.selectionId    = key;
		this.executionId    = myKeys;
		this.category	    = magic.getCategory();
		this.executionMagic = magic;
	}
	
	//攻撃魔法
	public Target( MonsterData monsterData , Integer myKeys , Integer key , Magic magic ) {
		
		this.skillName      = magic.getName();
		this.selectionName  = monsterData.getName();
		this.selectionId    = key;
		this.executionId    = myKeys;
		this.category	    = magic.getCategory();
		this.executionMagic = magic;
	}
	
	//死亡時の処理
	public Target( Integer myKeys ) {
		
		this.skillName     = "";
		this.selectionName = "";
		this.selectionId   = 0;
		this.executionId   = myKeys;
		this.category	   = "unable";
	}

}
