package com.example.rpg2.battle;

import java.util.ArrayList;
import java.util.List;

import com.example.rpg2.entity.Magic;

public class Selection {
	
	private String  name;
	private List<Integer> targetList = new ArrayList<>();
	private Integer targetId;
	private Integer InitiatorId;
	private Magic   magic;
	private String targetName;

	private Integer offensivePower;
	private Integer defense;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getTargetList() {
		return targetList;
	}
	public void setTargetList(List<Integer> targetList) {
		this.targetList = targetList;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getInitiatorId() {
		return InitiatorId;
	}
	public void setInitiatorId(Integer initiatorId) {
		InitiatorId = initiatorId;
	}
	public Magic getMagic() {
		return magic;
	}
	public void setMagic(Magic magic) {
		this.magic = magic;
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
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	
	
}
