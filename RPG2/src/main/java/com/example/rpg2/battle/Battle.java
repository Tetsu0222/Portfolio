package com.example.rpg2.battle;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Battle {
	
	
	Map<Integer , AllyData> allyMap;
	
	
	public Battle( AllyData allyData ) {
		allyMap = new HashMap<>();
		
	}

}
