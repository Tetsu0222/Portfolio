package com.example.rpg2.battle;


public class Attack extends Selection{
	
	//フィールド
	private String  name;
	private Integer offensivePower;
	private Integer defense;
	
	
	//コンストラクタ
	public Attack( AllyData allyData ,MonsterData monsterData) {
		this.offensivePower = allyData.getCurrentATK();
		this.name 			= "通常攻撃";
		this.defense 		= monsterData.getCurrentDEF();
	}
	
	
	//ダメージ計算
	public Integer attaclclac() {
		
		Integer damage = 10;
		
		return damage;
	}


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
	
	
	

}
