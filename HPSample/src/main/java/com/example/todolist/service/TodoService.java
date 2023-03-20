package com.example.todolist.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.example.todolist.common.Utils;
import com.example.todolist.entity.AttachedFile;
import com.example.todolist.form.CommentData;
import com.example.todolist.form.TaskData;
import com.example.todolist.form.TodoData;
import com.example.todolist.form.TodoQuery;
import com.example.todolist.repository.AttachedFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
	
	
	private final MessageSource messageSource;
	private final AttachedFileRepository attachedFileRepository;
	
    @Value("${attached.file.path}")
    private String ATTACHED_FILE_PATH;
	

    //Todo + Taskのチェック
    public boolean isValid( TodoData todoData , BindingResult result , boolean isCreate , Locale locale ) {
        boolean ans = true;

        //件名が全角スペースだけで構成されていたらエラー
        if ( !Utils.isBlank( todoData.getTitle() )) {
            if ( Utils.isAllDoubleSpace( todoData.getTitle() )) {
                FieldError fieldError = new FieldError( result.getObjectName(),
                										"title",
                										messageSource.getMessage( "DoubleSpace.todoData.title" , null , locale ));
                result.addError( fieldError );
                ans = false;
            }
        }

        //期限が""ならチェックしない
        String deadline = todoData.getDeadline();
        if ( !deadline.equals("") ) {
            // yyyy-mm-dd形式チェック
            if ( !Utils.isValidDateFormat( deadline )) {
                FieldError fieldError = new FieldError( result.getObjectName(),
                										"deadline",
                										messageSource.getMessage( "InvalidFormat.todoData.deadline" , null , locale ));
                result.addError( fieldError );
                ans = false;

            } else {
                //過去日付チェックは新規登録の場合のみ
                if ( isCreate ) {
                    //過去日付ならエラー
                    if ( !Utils.isTodayOrFurtureDate (deadline )) {
                        FieldError fieldError = new FieldError( result.getObjectName(),
                        										"deadline",
                        										messageSource.getMessage( "Past.todoData.deadline" , null , locale ));
                        result.addError( fieldError );
                        ans = false;
                    }
                }
            }
        }
        
        // Taskのチェック
        List<TaskData> taskList = todoData.getTaskList();
        
        if ( taskList != null ) {
            for ( int n = 0; n < taskList.size(); n++ ) {
                TaskData taskData = taskList.get(n);

                //タスクの件名が全角スペースだけで構成されていたらエラー
                if ( !Utils.isBlank( taskData.getTitle() )) {
                    if (Utils.isAllDoubleSpace( taskData.getTitle() )) {
                        FieldError fieldError = new FieldError( result.getObjectName(),
                        										"taskList[" + n + "].title",
                        										messageSource.getMessage( "DoubleSpace.todoData.title" , null , locale ));
                        result.addError( fieldError );
                        ans = false;
                    }
                }

                //タスク期限のyyyy-mm-dd形式チェック
                String taskDeadline = taskData.getDeadline();
                if ( !taskDeadline.equals("") && !Utils.isValidDateFormat( taskDeadline )) {
                    FieldError fieldError = new FieldError( result.getObjectName(),
                    										"taskList[" + n + "].deadline",
                    										messageSource.getMessage( "InvalidFormat.todoData.deadline" , null , locale ));
                    result.addError( fieldError );
                    ans = false;
                }
            }
        }
        return ans;
    }
    
    
    //新規コメント入力のチェック
    public boolean isValid( CommentData commentData , BindingResult result ,  Locale locale ) {
    	
        boolean ans = true;
        
        //名前が半角スペースだけ or "" ならエラー
        if ( Utils.isBlank( commentData.getName() )) {
             FieldError fieldError = new FieldError( result.getObjectName() ,
            		 								 "newTask.title",
          		  								  	 messageSource.getMessage( "NotBlank.taskData.title" , null , locale ));
            result.addError( fieldError );
            ans = false;
        
        } else {
            //名前が全角スペースだけで構成されていたらエラー
            if ( Utils.isAllDoubleSpace( commentData.getName() )) {
                FieldError fieldError = new FieldError( result.getObjectName() ,
              		  								    "newTask.title",
              		  								    messageSource.getMessage( "DoubleSpace.todoData.title" , null , locale ));
                result.addError( fieldError );
                ans = false;
            }
        }
        
        //Emailが半角スペースだけ or "" ならエラー
        if ( Utils.isBlank( commentData.getEmail() )) {
             FieldError fieldError = new FieldError( result.getObjectName() ,
            		 								 "newTask.title",
          		  								  	 messageSource.getMessage( "NotBlank.taskData.title" , null , locale ));
            result.addError( fieldError );
            ans = false;
        
        } else {
            //Emailが全角スペースだけで構成されていたらエラー
            if ( Utils.isAllDoubleSpace( commentData.getEmail() )) {
                FieldError fieldError = new FieldError( result.getObjectName() ,
              		  								    "newTask.title",
              		  								    messageSource.getMessage( "DoubleSpace.todoData.title" , null , locale ));
                result.addError( fieldError );
                ans = false;
            }
        }
        
        //コメントが半角スペースだけ or "" ならエラー
        if ( Utils.isBlank( commentData.getComment() )) {
             FieldError fieldError = new FieldError( result.getObjectName() ,
            		 								 "newTask.title",
          		  								  	 messageSource.getMessage( "NotBlank.taskData.title" , null , locale ));
            result.addError( fieldError );
            ans = false;
        
        } else {
            //Emailが全角スペースだけで構成されていたらエラー
            if ( Utils.isAllDoubleSpace( commentData.getComment() )) {
                FieldError fieldError = new FieldError( result.getObjectName() ,
              		  								    "newTask.title",
              		  								    messageSource.getMessage( "DoubleSpace.todoData.title" , null , locale ));
                result.addError( fieldError );
                ans = false;
            }
        }
        
        return ans;
    }

    
    
    //検索条件のチェック
    public boolean isValid( TodoQuery todoQuery , BindingResult result , Locale locale ) {
        boolean ans = true;

        // 期限:開始の形式をチェック
        String date = todoQuery.getDeadlineFrom();
        if (!date.equals("") && !Utils.isValidDateFormat(date)) {
            FieldError fieldError = new FieldError( result.getObjectName(),
            										"deadlineFrom",
            										messageSource.getMessage( "InvalidFormat.todoQuery.deadlineFrom" , null , locale ));
            result.addError(fieldError);
            ans = false;
        }

        // 期限:終了の形式をチェック
        date = todoQuery.getDeadlineTo();
        if ( !date.equals("") && !Utils.isValidDateFormat( date )) {
        	FieldError fieldError = new FieldError( result.getObjectName() ,
        											"deadlineTo",
        											messageSource.getMessage( "InvalidFormat.todoQuery.deadlineTo" , null , locale ));
            result.addError( fieldError );
            ans = false;
        }
        return ans;
    }
    

    //新規Taskのチェック
    public boolean isValid( TaskData taskData , BindingResult result , Locale locale ) {
        boolean ans = true;
  
          //タスクの件名が半角スペースだけ or "" ならエラー
          if ( Utils.isBlank( taskData.getTitle() )) {
              FieldError fieldError = new FieldError( result.getObjectName() ,
            		  								  "newTask.title",
            		  								  messageSource.getMessage( "NotBlank.taskData.title" , null , locale ));
              result.addError( fieldError );
              ans = false;
          
          } else {
              //タスクの件名が全角スペースだけで構成されていたらエラー
              if ( Utils.isAllDoubleSpace( taskData.getTitle() )) {
                  FieldError fieldError = new FieldError( result.getObjectName() ,
                		  								  "newTask.title",
                		  								  messageSource.getMessage( "DoubleSpace.todoData.title" , null , locale ));
                  result.addError( fieldError );
                  ans = false;
              }
          }
  
        //期限が""ならチェックしない
        String deadline = taskData.getDeadline();
        if ( deadline.equals("") ) {
            return ans;
        }
  
        //期限のyyyy-mm-dd形式チェック
        if ( !Utils.isValidDateFormat (deadline )) {
            FieldError fieldError = new FieldError( result.getObjectName(),
            										"newTask.deadline",
            										messageSource.getMessage( "InvalidFormat.todoData.deadline" , null , locale ));
            result.addError( fieldError );
            ans = false;
  
        } else {
            //過去日付ならエラー
            if ( !Utils.isTodayOrFurtureDate( deadline )) {
                FieldError fieldError = new FieldError( result.getObjectName(),
                										"newTask.deadline",
                										messageSource.getMessage( "Past.todoData.deadline" , null , locale ));
                result.addError( fieldError );
                ans = false;
            }
        }
  
        return ans;
    }


    //添付ファイル格納処理
    public void saveAttachedFile( int todoId , String note , MultipartFile fileContents ) {
        //アップロード元ファイル名
        String fileName = fileContents.getOriginalFilename();

        //格納フォルダの存在チェック
        File uploadDir = new File( ATTACHED_FILE_PATH );
        if ( !uploadDir.exists() ) {
            //ないのでディレクトリ作成
            uploadDir.mkdirs();
        }

        //添付ファイルの格納(upload)時刻を取得
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
        String createTime = sdf.format( new Date() );

        //テーブルへ格納するインスタンス作成
        AttachedFile af = new AttachedFile();
        af.setTodoId    ( todoId     );
        af.setFileName  ( fileName   );
        af.setCreateTime( createTime );
        af.setNote      ( note );

        //アップロードファイルの内容を取得
        byte[] contents;
        try ( BufferedOutputStream bos = new BufferedOutputStream( 
        		new FileOutputStream( Utils.makeAttahcedFilePath( ATTACHED_FILE_PATH , af ) ))) {
            //アップロードファイルを書き込む
            contents = fileContents.getBytes();
            bos.write( contents );

            //テーブルへ記録
            attachedFileRepository.saveAndFlush( af );

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    //添付ファイル削除処理
    public void deleteAttachedFile( int afId ) {
        AttachedFile af = attachedFileRepository.findById( afId ).get();
        File file = new File( Utils.makeAttahcedFilePath( ATTACHED_FILE_PATH , af ));
        file.delete();
    }
    

    //添付ファイル削除処理(Todo.idで削除)
    public void deleteAttachedFiles( Integer todoId ) {
        File file;
        List<AttachedFile> attachedFiles = attachedFileRepository.findByTodoIdOrderById( todoId );
        for ( AttachedFile af : attachedFiles ) {
            file = new File(Utils.makeAttahcedFilePath( ATTACHED_FILE_PATH , af ));
            file.delete();
        }
    }
}