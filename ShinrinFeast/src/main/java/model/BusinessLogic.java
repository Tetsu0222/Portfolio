package model;

import java.util.List;

public class BusinessLogic {
	
	public boolean executeInsertSurvey( ShinrinDto dto ){
		return new ShinrinDao().doInsert( dto );
	}
	
	public List<ShinrinDto> executeSelect(){
		return new ShinrinDao().doSelect();
	}
}