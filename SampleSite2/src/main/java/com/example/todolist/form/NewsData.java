package com.example.todolist.form;


import com.example.todolist.entity.News;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsData {
	
	private Integer id;
	private String title;
	private String text;
	
	public News toEntity() {
		News ne = new News();
		ne.setId     ( id      );
		ne.setTitle  ( title    );
		ne.setText   ( text   );
		return ne;
	}

}
