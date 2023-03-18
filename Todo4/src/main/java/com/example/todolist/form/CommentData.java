package com.example.todolist.form;

import java.sql.Timestamp;

import com.example.todolist.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentData {

	private Integer   id;
	private String    name;
	private String    email;
	private String    comment;
	private Timestamp time;
	
	
	public Comment toEntity() {
		Comment com = new Comment();
		com.setId     ( id      );
		com.setName   ( name    );
		com.setEmail  ( email   );
		com.setComment( comment );
		com.setTime   ( new Timestamp( System.currentTimeMillis() ));
		return com;
	}

}
