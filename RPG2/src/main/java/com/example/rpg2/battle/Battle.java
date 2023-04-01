package com.example.rpg2.battle;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class Battle {
	
	MonsterData monsterData;
	
	Map<Integer, AllyData> partyMap;
	Random random = new Random();
	
	public Battle( List<AllyData> partyList , MonsterData monsterData ) {
		this.monsterData = monsterData;
		
		this.partyMap = IntStream.range( 0 , partyList.size() )
							.boxed()
							.collect( Collectors.toMap( s -> s , s -> partyList.get( s ) ));
		
		
	}

}
