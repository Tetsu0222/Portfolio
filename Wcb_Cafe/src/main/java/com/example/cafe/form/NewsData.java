package com.example.cafe.form;

import java.util.ArrayList;
import java.util.List;

import com.example.cafe.entity.Content;
import com.example.cafe.entity.News;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsData {
	
	//フィールド バリデ未設定
	private Integer id;
	private String  title;
	
	//関連テーブルの入出力用リスト
	private List<ContentData> contentList;
	
	//関連テーブルのデータ登録用
	private ContentData newContent;
	
	
	//コンストラクタ
	//id検索後のNewsオブジェクトを引数に自オブジェクトを生成
	public NewsData( News news ) {
		
		this.id    = news.getId();
		this.title = news.getTitle();
		
        //登録済Content
        this.contentList = new ArrayList<>();
        for ( Content con : news.getContentList() ) {
            this.contentList.add( new ContentData( con.getId() , con.getTitle() , con.getContent() , con.getTime() ));
        }

        //追加用Task
        newContent = new ContentData();
		
	}
	
	
	//自オブジェクトのデータ（入力値）からNewsオブジェクトを生成(Entity生成）
	public News toEntity() {
		
		News news = new News();
		news.setId     ( id    );
		news.setTitle  ( title );
		
        //Content部分
        Content con;
        if ( contentList != null ) { 
            for ( ContentData contentData : contentList ) {
                con = new Content( contentData.getId() , null , contentData.getTitle() , contentData.getContent() , contentData.getTime() );
                news.addContent( con );
            }
        }
        
		return news;
	}

	
    public Content toContentEntity() {
    	
        Content con = new Content();
        con.setId     ( newContent.getId()      );
        con.setTitle  ( newContent.getTitle()   );
        con.setContent( newContent.getContent() );
        con.setTime   ( newContent.getTime()    );
  
        return con;
    }
    
}
