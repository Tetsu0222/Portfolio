package com.example.rpg2.battle;

import java.util.ArrayList;
import java.util.List;

import com.example.rpg2.entity.Magic;

public class MagicData extends Selection{
	
	private String  name;
	private String targetName;
	private List<Integer> targetList = new ArrayList<>();
	private Integer targetId;
	private Integer InitiatorId;
	private Magic   magic;
	
	
	public MagicData(  Magic magic , Integer key ) {
		this.magic 		 = magic;
		this.InitiatorId = key;
		this.name 		 = magic.getName();
	}
	
	
	public void Activation( Battle battle ) {
		
		String category = magic.getCategory();
		
		//攻撃魔法を発動
		if( category.equals( "attackmagic" ) ) {
			int HP = battle.monsterData.getCurrentHp();	//編集点
			HP -= magic.getPoint();
			
			if( HP > 0 ){
				battle.monsterData.setCurrentHp( HP );
				
			}else{
				battle.monsterData.setCurrentHp( 0 );
			}
			
		//回復魔法を発動
		}else if( category.equals( "recoverymagic" ) && magic.getPercentage() == 0 ) {
			int HP = battle.getPartyMap().get( targetId ).getCurrentHp();
			HP += magic.getPoint();
			
			if( battle.getPartyMap().get( targetId ).getMaxHP() < HP ) {
				HP = battle.getPartyMap().get( targetId ).getMaxHP();
			}
			battle.getPartyMap().get( targetId ).setCurrentHp( HP );
			
		}else if( category.equals( "recoverymagic" ) ){
			double HP = battle.getPartyMap().get( targetId ).getMaxHP() * magic.getPercentage();
			battle.getPartyMap().get( targetId ).setCurrentHp( (int)HP );
		}
		
		
		//MP消費処理
		int MP = battle.partyMap.get( InitiatorId ).getCurrentMp();
		MP -= magic.getMp();
		battle.partyMap.get( InitiatorId ).setCurrentMp( MP );
	}
	
	


	public Integer getTargetId() {
		return targetId;
	}


	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTargetName() {
		return targetName;
	}


	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	
	

}
