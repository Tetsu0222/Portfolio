package com.example.cafe.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table( name = "newscontent" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" )
	private Integer id;
	
    @ManyToOne
    @JoinColumn( name = "c_id" )
	private News news;
	
	@Column( name = "newstitle" )
	private String title;
	
	@Column( name = "content" )
	private String content;
	
    @Column( name = "time" )
    private Date time;
	
	

}
