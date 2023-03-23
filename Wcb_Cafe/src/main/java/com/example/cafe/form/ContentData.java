package com.example.cafe.form;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentData {
	
	private Integer id   ;
	private String  title;
	private String  content ;
    private Date    time ;

}
