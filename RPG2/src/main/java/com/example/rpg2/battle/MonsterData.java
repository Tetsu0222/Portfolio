package com.example.rpg2.battle;

import com.example.rpg2.entity.Monster;

import lombok.Data;

@Data
public class MonsterData {
	
	private String  name;
	
	private Integer maxHP;
	private Integer maxMP;
	private Integer defaultATK;
	private Integer defaultDEF;
	private Integer defaultSPE;
	private Integer pattern;
	private Integer actions;
	
	private Integer currentHp;
	private Integer currentMp;
	private Integer currentATK;
	private Integer currentDEF;
	private Integer currentSPE;
	
	
	public MonsterData( Monster monster ) {
		
		this.name = monster.getName();
		
		//固定ステータスの設定
		this.maxHP = monster.getHp();
		this.maxMP = monster.getMp();
		this.defaultATK = monster.getAtk();
		this.defaultDEF = monster.getDef();
		this.defaultSPE = monster.getSpe();
		
		//変動ステータスの設定
		this.currentHp  = monster.getHp();
		this.currentMp  = monster.getMp();
		this.currentATK = monster.getAtk();
		this.currentDEF = monster.getDef();
		this.currentSPE = monster.getSpe();
		
	}

}
