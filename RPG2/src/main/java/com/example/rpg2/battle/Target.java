package com.example.rpg2.battle;

import com.example.rpg2.entity.Magic;

import lombok.Data;

@Data
public class Target {
	
	private String  skillName;
	private String  selectionName;
	private Integer selectionId;
	private Action  action;
	
	
	//通常攻撃
	public Target( AllyData allyData , MonsterData monsterData , Integer key ) {
		
		//this.action = new Action( "通常攻撃" );
		this.skillName     = "通常攻撃";
		this.selectionName = monsterData.getName();
		this.selectionId   = key;
	}
	
	//味方への魔法
	public Target( AllyData allyData , AllyData receptionAllyData , Integer key , Magic magic ) {
		
		//this.action = new Action( allyData , receptionAllyData , magic );
		this.skillName     = magic.getName();
		this.selectionName = receptionAllyData.getName();
		this.selectionId   = key;
	}

}
