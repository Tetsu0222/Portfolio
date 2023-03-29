package com.example.ec.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.ec.entity.Goods;
import com.example.ec.form.GoodsQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoodsDaoImpl implements GoodsDao{
	
	
	private final EntityManager entityManager;

	@Override
	public List<Goods> findByCriteria( GoodsQuery goodsQuery ) {
		
		//おまじない
        CriteriaBuilder       builder   = entityManager.getCriteriaBuilder();
        CriteriaQuery<Goods> query      = builder.createQuery( Goods.class );
        Root<Goods>          root       = query.from( Goods.class );
        
        //後でListから配列へ変換 複数条件検索に対応予定
        List<Predicate>       predicates = new ArrayList<>();
        
        //名前検索
        String name = "";
        if( goodsQuery.getName().length() > 0 ) {
            name = "%" + goodsQuery.getName() + "%";
        }else{
            name = "%";
        }
        
        predicates.add( builder.like( root.get( "name" ) , name ));
        
        //Listから配列へ変換
        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray( predArray );
        
        //検索内容を生成
        query = query.select( root ).where( predArray ).orderBy( builder.asc( root.get( "id" ) ));
        
        //検索実行
        TypedQuery<Goods> typedQuery = entityManager.createQuery( query );
        
        //検索結果をListで取得
        List<Goods> goodsList = typedQuery.getResultList();
        
		return goodsList;
		
	}
	
	

}
