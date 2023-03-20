package com.example.todolist.form;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.todolist.common.Utils;
import com.example.todolist.entity.AttachedFile;
import com.example.todolist.entity.Category;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.Todo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoData {
    private Integer id;

    @NotBlank
    private String title;

    @NotNull
    private Integer importance;

    @Min(value = 0)
    private Integer urgency;

    private String deadline;
    private String done;
    
    @Min(value = 1)
    private Integer categoryId;
    
    @Valid
    private List<TaskData> taskList;
    
    private TaskData newTask;
    
    private List<AttachedFileData> attachedFileList;

    //入力データからTodo Entityを生成して返す
    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setId        ( id         );
        todo.setTitle     ( title      );
        todo.setImportance( importance );
        todo.setUrgency   ( urgency    );
        todo.setDone      ( done       );
        todo.setCategory  ( new Category( categoryId ) );

        SimpleDateFormat sdFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        long ms;
        try {
            ms = sdFormat.parse( deadline ).getTime();
            todo.setDeadline( new Date( ms ));
        } catch ( ParseException e ) {
            todo.setDeadline( null );
        }

        //Task部分
        Date date;
        Task task;
       if ( taskList != null ) { 
            for ( TaskData taskData : taskList ) {
                date = Utils.str2dateOrNull( taskData.getDeadline() );
                task = new Task( taskData.getId() , null , taskData.getTitle() , date, taskData.getDone() );
                todo.addTask( task );
            }
        }
        return todo;
    }
    
    //Todoの内容から入力画面へ渡すTodoDataを生成する
    public TodoData( Todo todo , List<AttachedFile> attachedFiles ) {
        //Todo部分
        this.id         = todo.getId();
        this.title      = todo.getTitle();
        this.importance = todo.getImportance();
        this.urgency    = todo.getUrgency();
        this.deadline   = Utils.date2str( todo.getDeadline() );
        this.done       = todo.getDone();
        this.categoryId = todo.getCategory().getId();

        //登録済Task
        this.taskList = new ArrayList<>();
        String dt;
        for ( Task task : todo.getTaskList() ) {
            dt = Utils.date2str( task.getDeadline() );
            this.taskList.add(
                new TaskData( task.getId() , task.getTitle() , dt , task.getDone() ));
        }

        //追加用Task
        newTask = new TaskData();
        
        //添付ファイル 
        attachedFileList = new ArrayList<>();
        String fileName;
        String fext;
        String contentType;
        boolean isOpenNewWindow;
        for ( AttachedFile af : attachedFiles ) {
            //ファイル名
            fileName = af.getFileName();
            //拡張子
            fext = fileName.substring( fileName.lastIndexOf(".") + 1 );
            //Content-Type
            contentType = Utils.ext2contentType( fext );
            //別Windowで表示するか？
            isOpenNewWindow = contentType.equals("") ? false : true;
            attachedFileList.add( new AttachedFileData( af.getId() , fileName , af.getNote() , isOpenNewWindow ));
        }

    }
    
    //TodoForm画面の新規タスク入力行の内容からTaskオブジェクトを生成して返す
    public Task toTaskEntity() {
        Task task = new Task();
        task.setId      ( newTask.getId()    );
        task.setTitle   ( newTask.getTitle() );
        task.setDone    ( newTask.getDone()  );
        task.setDeadline( Utils.str2date( newTask.getDeadline() ));
  
        return task;
    }

}
