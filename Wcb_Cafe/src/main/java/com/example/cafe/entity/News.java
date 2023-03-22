package com.example.cafe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table( name = "newscategory" )
@Data
//@ToString( exclude="contentList" )
public class News {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "title" )
    private String title;
    
    /*
    @OneToMany( mappedBy = "todo", cascade = CascadeType.ALL )
    @OrderBy( "id asc")
    private List<Task> taskList= new ArrayList<>();
    
    
    public void addTask( Task task ) {
        task.setTodo( this );
        taskList.add( task );
    }
    
    */
}
