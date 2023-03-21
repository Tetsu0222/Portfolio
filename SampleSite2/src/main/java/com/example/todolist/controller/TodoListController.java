package com.example.todolist.controller;

import java.util.List;
//import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.common.OpMsg;
import com.example.todolist.dao.TodoDaoImpl;
import com.example.todolist.entity.AttachedFile;
import com.example.todolist.entity.Category;
import com.example.todolist.entity.Comment;
import com.example.todolist.entity.News;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.Todo;
import com.example.todolist.form.NewsData;
import com.example.todolist.form.TodoData;
import com.example.todolist.form.TodoQuery;
import com.example.todolist.repository.AttachedFileRepository;
import com.example.todolist.repository.CategoryRepository;
import com.example.todolist.repository.CommentRepository;
import com.example.todolist.repository.NewsRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import com.example.todolist.view.TodoPdf;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoListController {
	
	
	//フィールド 初回インスタンス
    private final TodoRepository         todoRepository;
    private final TaskRepository         taskRepository;
    private final TodoService            todoService;
    private final HttpSession            session;
    private final MessageSource          messageSource;
    private final AttachedFileRepository attachedFileRepository;
    private final CommentRepository      commentRepository;
    private final CategoryRepository     categoryRepository;
    private final ServletContext         application;
    private final NewsRepository         newsRepository;

    //フィールド 2回目のインスタンス
    @PersistenceContext
    private EntityManager entityManager;
    TodoDaoImpl todoDaoImpl;

    //インジェクション終了後に実行
    @PostConstruct
    public void init() {
        todoDaoImpl = new TodoDaoImpl( entityManager );
    }

    
    //ToDo一覧表示
    @GetMapping( "/todo" )
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showTodoList( ModelAndView mv ,
                                      @PageableDefault( page = 0 , size = 5 , sort = "id" ) Pageable pageable ) {
        //sessionから前回の検索条件を取得
        TodoQuery todoQuery = (TodoQuery)session.getAttribute( "todoQuery" );
        if ( todoQuery == null ) {
            //なければ初期値を使う
            todoQuery = new TodoQuery();
            session.setAttribute( "todoQuery" , todoQuery );
        }

        //sessionから前回のpageableを取得
        Pageable prevPageable = (Pageable)session.getAttribute( "prevPageable" );
        if (prevPageable == null) {
            //なければ@PageableDefaultを使う
            prevPageable = pageable;
            session.setAttribute( "prevPageable" , prevPageable );
        }

        mv.setViewName( "todoList" );

        //Todo検索
        Page<Todo> todoPage = todoDaoImpl.findByCriteria( todoQuery , prevPageable );
        mv.addObject( "todoQuery", todoQuery); //検索条件
        mv.addObject( "todoPage" , todoPage ); //page情報
        mv.addObject( "todoList" , todoPage.getContent() ); //検索結果
        
        //カテゴリー
        @SuppressWarnings( "unchecked" )
        List<Category> categoryList = (List<Category>)application.getAttribute( "categoryList" );
        if ( categoryList == null ) {
             categoryList = categoryRepository.findAll();
             categoryList.add( 0 , new Category( 0 , "---------" ));
             application.setAttribute( "categoryList" , categoryList );
        }
        return mv;
    }

    //ToDo表示
    @GetMapping( "/todo/{id}" )
    @PreAuthorize("isAuthenticated()")
    public ModelAndView todoById( @PathVariable( name = "id" ) int id , ModelAndView mv ) {
        mv.setViewName( "todoForm" );
        //ToDo取得(Todo + Task)
        Todo todo = todoRepository.findById( id ).get();
        //添付ファイル情報取得
        List<AttachedFile> attachedFiles = attachedFileRepository.findByTodoIdOrderById( id );
        //表示用データ作成
        mv.addObject( "todoData", new TodoData( todo , attachedFiles )); 
        session.setAttribute( "mode" , "update" );
        return mv;
    }

    //ToDo入力フォーム表示
    @PostMapping( "/todo/create/form" )
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createTodo( ModelAndView mv ) {
        mv.setViewName( "todoForm" );
        mv.addObject  ( "todoData" , new TodoData() );
        session.setAttribute( "mode" , "create" );
        return mv;
    }

    //ToDo追加処理
    @PostMapping( "/todo/create/do" )
    @PreAuthorize("isAuthenticated()")
    public String createTodo( @ModelAttribute @Validated TodoData todoData , BindingResult result ,
                              Model model , RedirectAttributes redirectAttributes , Locale locale ) {
        //エラーチェック
        boolean isValid = todoService.isValid( todoData , result , true , locale );
        if ( !result.hasErrors() && isValid ) {
            //エラーなし -> 追加
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush( todo );
            
            //追加完了メッセージをセットしてリダイレクト
            String msg = messageSource.getMessage( "msg.i.todo_created" , null , locale );
            redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ) );

            return "redirect:/todo/" + todo.getId(); 

        } else {
            //エラーあり -> エラーメッセージをセット
            String msg = messageSource.getMessage( "msg.e.input_something_wrong" , null , locale );
            model.addAttribute( "msg" , new OpMsg( "E" , msg ));
            return "todoForm";
        }
    }

    //ToDo更新処理
    @PostMapping( "/todo/update" )
    @PreAuthorize("isAuthenticated()")
    public String updateTodo( @ModelAttribute @Validated TodoData todoData , BindingResult result ,
                              Model model , RedirectAttributes redirectAttributes , 
                              Locale locale ) {
        //エラーチェック
        boolean isValid = todoService.isValid( todoData , result , false , locale );
        if ( !result.hasErrors() && isValid ) {
            //エラーなし -> 更新
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush( todo );
            
            //更新完了メッセージをセットしてリダイレクト
            String msg = messageSource.getMessage( "msg.i.todo_updated" , null , locale );
            redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ));
            return "redirect:/todo/" + todo.getId();

        } else {
            //エラーあり -> エラーメッセージをセット
            String msg = messageSource.getMessage( "msg.e.input_something_wrong" , null , locale );
            model.addAttribute("msg", new OpMsg( "E" , msg ));

            return "todoForm";
        }
    }

    //ToDo削除処理
    @PostMapping( "/todo/delete" )
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTodo( @ModelAttribute TodoData todoData , 
    						  RedirectAttributes redirectAttributes , 
    						  Locale locale) {
        Integer todoId = todoData.getId();
        
        //添付ファイルを削除
        todoService.deleteAttachedFiles( todoId );

        //attached_fileテーブルから削除
        List<AttachedFile> attachedFiles = attachedFileRepository.findByTodoIdOrderById( todoId );
        attachedFileRepository.deleteAllInBatch( attachedFiles );
        
        //削除
        todoRepository.deleteById( todoData.getId() );
        
        //削除完了メッセージをセットしてリダイレクト
        String msg = messageSource.getMessage( "msg.i.todo_deleted" , null , locale );
        redirectAttributes.addFlashAttribute( "msg", new OpMsg( "I" , msg ));

        return "redirect:/todo";
    }

    //ToDo検索処理
    @PostMapping( "/todo/query" )
    @PreAuthorize("isAuthenticated()")
    public ModelAndView queryTodo( @ModelAttribute TodoQuery todoQuery , BindingResult result ,
                                   @PageableDefault( page = 0 , size = 5 , sort = "id" ) Pageable pageable ,
                                   ModelAndView mv , Locale locale) {
        mv.setViewName( "todoList" );

        Page<Todo> todoPage = null;
        if ( todoService.isValid( todoQuery , result , locale )) {
            //エラーがなければ検索
            todoPage = todoDaoImpl.findByCriteria( todoQuery , pageable );

            //入力された検索条件をsessionへ保存
            session.setAttribute( "todoQuery" , todoQuery);

            mv.addObject( "todoPage" , todoPage );
            mv.addObject( "todoList" , todoPage.getContent() );
            
            //該当なかったらメッセージを表示
            if ( todoPage.getContent().size() == 0 ) {
                String msg = messageSource.getMessage( "msg.w.todo_not_found" , null , locale );
                mv.addObject( "msg" , new OpMsg( "W" , msg ));
            }

        } else {
            //検索条件エラーあり -> エラーメッセージをセット
            String msg = messageSource.getMessage( "msg.e.input_something_wrong" , null , locale );
            mv.addObject( "msg" , new OpMsg( "E" , msg ));
            
            mv.addObject( "todoPage" , null );
            mv.addObject( "todoList" , null );
        }
        return mv;
    }

    //ページリンク押下時
    @GetMapping( "/todo/query" )
    @PreAuthorize("isAuthenticated()")
    public ModelAndView queryTodo( @PageableDefault( page = 0 , size = 5 , sort = "id" ) Pageable pageable ,
                                   ModelAndView mv ) {
        //現在のページ位置を保存
        session.setAttribute( "prevPageable", pageable );

        mv.setViewName( "todoList" );

        //sessionに保存されている条件で検索
        TodoQuery todoQuery = (TodoQuery)session.getAttribute( "todoQuery" );
        Page<Todo> todoPage = todoDaoImpl.findByCriteria( todoQuery , pageable );

        mv.addObject( "todoQuery" , todoQuery ); //検索条件
        mv.addObject( "todoPage"  , todoPage  ); //page情報
        mv.addObject( "todoList"  , todoPage.getContent() ); //検索結果

        return mv;
    }

    //ToDo一覧へ戻る
    @PostMapping( "/todo/cancel" )
    @PreAuthorize("isAuthenticated()")
    public String cancel() {
        return "redirect:/todo";
    }

    //Task追加処理
    @PostMapping( "/task/create" )
    @PreAuthorize("isAuthenticated()")
    public String createTask( @ModelAttribute TodoData todoData ,
                               BindingResult result , Model model ,
                               RedirectAttributes redirectAttributes , Locale locale ) {
        //エラーチェック
        boolean isValid = todoService.isValid( todoData.getNewTask() , result , locale );
        if ( isValid ) {
            //エラーなし
            Todo todo = todoData.toEntity();
            Task task = todoData.toTaskEntity();
            task.setTodo( todo );
            taskRepository.saveAndFlush( task );
  
            //追加完了メッセージをセットしてリダイレクト
            String msg = messageSource.getMessage( "msg.i.task_created" , null , locale );
            redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ));
            return "redirect:/todo/" + todo.getId();
  
        } else {
            //エラーあり -> エラーメッセージをセット
            String msg = messageSource.getMessage( "msg.e.input_something_wrong" , null , locale );
            model.addAttribute( "msg" , new OpMsg( "E" , msg ));
            return "todoForm";
        }
    }
    
    //Task削除処理
    @GetMapping( "/task/delete" )
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTask( @RequestParam( name = "task_id" ) int taskId ,
                              @RequestParam( name = "todo_id" ) int todoId , 
                              RedirectAttributes redirectAttributes , Locale locale ) {
        //Taskを削除
        taskRepository.deleteById( taskId );

        //削除完了メッセージをセットしてリダイレクト
        String msg = messageSource.getMessage( "msg.i.task_deleted" ,  null , locale );
        redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ));
        return "redirect:/todo/" + todoId;
    }
    
    //添付ファイルをアップロードする
    @PostMapping( "/todo/af/upload" )
    @PreAuthorize("isAuthenticated()")
    public String uploadAttachedFile( @RequestParam( "todo_id" ) int todoId ,
                      				  @RequestParam( "note"    ) String note ,
                      				  @RequestParam( "file_contents" ) MultipartFile fileContents ,
                      				  RedirectAttributes redirectAttributes , 
                      				  Locale locale ) {
        //ファイルが空？
        if ( fileContents.isEmpty() ) {
            //ファイルemptyのメッセージをセット
            String msg = messageSource.getMessage( "msg.w.attachedfile_empty" , null , locale );
            redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "W" , msg ) );
  
        } else {
            //ファイルを保存する
            todoService.saveAttachedFile( todoId , note , fileContents );
            //Upload完了メッセージをセット
            String msg = messageSource.getMessage( "msg.i.attachedfile_uploaded" , null , locale );
            redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ) );
        }
        //再表示
        return "redirect:/todo/" + todoId;
    }
    
    //添付ファイルを削除する
    @GetMapping( "/todo/af/delete" )
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAttachedFile( @RequestParam( name = "af_id"   ) int afId  ,
                                      @RequestParam( name = "todo_id" ) int todoId,
                                      RedirectAttributes redirectAttributes ,
                                      Locale locale ) {
        //添付ファイルを削除
        todoService.deleteAttachedFile( afId );
        //attached_fileテーブルから削除
        attachedFileRepository.deleteById( afId );

        //削除完了メッセージをセットしてリダイレクト
        String msg = messageSource.getMessage( "msg.i.attachedfile_deleted" , null , locale );
        redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ) );
        return "redirect:/todo/" + todoId;
    }
    
    
    //コメント画面へ遷移
	@GetMapping( "/todo/comment" )
	@PreAuthorize("isAuthenticated()")
	public ModelAndView comment( ModelAndView mv ) {
			mv.setViewName( "comment" );
			List<Comment> commentList = commentRepository.findAll();
			mv.addObject( "commentList", commentList );
			return mv;
	}
	
    //コメントを削除する
    @GetMapping( "/comment/delete" )
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAttachedFile( @RequestParam( name = "comment_id"   ) int commentId  ,
    								  RedirectAttributes redirectAttributes ,
                                      Locale locale ) {
        //テーブルから削除
        commentRepository.deleteById( commentId );

        //削除完了メッセージをセットしてリダイレクト
        String msg = messageSource.getMessage( "msg.i.comment_deleted" , null , locale );
        redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ) );
        return "redirect:/todo/comment";
    }
	
	
    @GetMapping( "/todo/pdf" )
    @PreAuthorize("isAuthenticated()")
    public TodoPdf writeTodoPdf( TodoPdf pdf ) {
        List<Todo> todoList = todoRepository.findAllByOrderById();
        pdf.addStaticAttribute( "todoList", todoList );
        return pdf;
    }
    
    
    //ニュース画面へ遷移
	@GetMapping( "/todo/news" )
	@PreAuthorize("isAuthenticated()")
	public ModelAndView news( ModelAndView mv ) {
			mv.setViewName( "NewsFile" );
			
			List<News> newsList = newsRepository.findAll();
			mv.addObject( "newsList" , newsList       );
			mv.addObject( "newsData" , new NewsData() );
			return mv;
	}
	
	//ニュース投稿
	@PostMapping( "/news/create" )
	@PreAuthorize("isAuthenticated()")
	public String createComment( @ModelAttribute @Validated NewsData newsData ,
								 BindingResult result , 
								 Model model ,
								 Locale locale ) {
        //boolean isValid = todoService.isValid( newsData , result , locale );
			
        //if ( isValid ) {
        	News news = newsData.toEntity();
        	newsRepository.saveAndFlush( news );
        	
        //} else {
            //エラーあり -> エラーメッセージをセット
            //String msg = messageSource.getMessage( "msg.e.input_something_wrong" , null , locale );
            //model.addAttribute( "msg" , new OpMsg( "E" , msg ));
            
            //return "/todo/news";
        //}
        
		return "redirect:/todo/news";
		
	}
	
    //ニュースを削除する
    @GetMapping( "/news/delete" )
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteNews( @RequestParam( name = "news_id"   ) int newsId  ,
    						  RedirectAttributes redirectAttributes ,
                              Locale locale ) {
        //テーブルから削除
        newsRepository.deleteById( newsId );

        //削除完了メッセージをセットしてリダイレクト
        String msg = messageSource.getMessage( "msg.i.comment_deleted" , null , locale );
        redirectAttributes.addFlashAttribute( "msg" , new OpMsg( "I" , msg ) );
        return "redirect:/todo/news";
    }
	
}
