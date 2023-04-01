package com.example.rpg2.battle;


public class Attack extends Selection{
	
	//フィールド
	private String  name;
	private Integer offensivePower;
	private Integer defense;
	private Integer targetId;
	private String  targetName;
	
	
	//コンストラクタ
	public Attack( AllyData allyData , MonsterData monsterData ) {
		this.offensivePower = allyData.getCurrentATK();
		this.name 			= "通常攻撃";
		this.targetName		= monsterData.getName();
	}
	
	
	//ダメージ計算
	public Integer attaclclac() {
		
		Integer damage = 10;
		
		return damage;
	}


	//getterとsetter
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getOffensivePower() {
		return offensivePower;
	}


	public void setOffensivePower(Integer offensivePower) {
		this.offensivePower = offensivePower;
	}


	public Integer getDefense() {
		return defense;
	}


	public void setDefense(Integer defense) {
		this.defense = defense;
	}


	public Integer getTargetId() {
		return targetId;
	}


	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}


	public String getTargetName() {
		return targetName;
	}


	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	
	

}
