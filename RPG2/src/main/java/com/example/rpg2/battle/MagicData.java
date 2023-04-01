package com.example.rpg2.battle;

import com.example.rpg2.entity.Magic;

public class MagicData extends Selection{
	
	private String name;
	
	
	public MagicData( Magic magic ) {
		
		this.name = magic.getName();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
