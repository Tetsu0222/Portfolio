package com.example.cafe.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table( name = "newscategory" )
@Data
@ToString( exclude="contentList" )
public class News {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "title" )
    private String title;
    
    //mappedByは結合先の自身を示す変数名を指定
    @OneToMany( mappedBy = "news" , cascade = CascadeType.ALL )
    @OrderBy( "id asc")
    private List<Content> contentList= new ArrayList<>();
    
    
    public void addContent( Content con ) {
    	
    }
    
    
}
