package com.example.todolist.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table( name = "todo" )
@Data
@ToString( exclude="taskList" )
public class Todo {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "title" )
    private String title;

    @Column( name = "importance" )
    private Integer importance;

    @Column( name = "urgency" )
    private Integer urgency;

    @Column( name = "deadline" )
    private Date deadline;

    @Column( name = "done" )
    private String done;
    
    @ManyToOne
    @JoinColumn( name = "category_id" )
    private Category category;
    
    @OneToMany( mappedBy = "todo", cascade = CascadeType.ALL )
    @OrderBy( "id asc")
    private List<Task> taskList= new ArrayList<>();
    
    // Todoへの参照設定
    public void addTask( Task task ) {
        task.setTodo( this );
        taskList.add( task );
    }
}
