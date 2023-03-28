package com.example.bakery.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.bakery.entity.Bakery;
import com.example.bakery.form.BakeryQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BakeryDaoImp implements BakeryDao {
	
	private final EntityManager entityManager;
	
	
	@Override
	public List<Bakery> findByCriteria( BakeryQuery bakeryQuery ) {

		//おまじない
        CriteriaBuilder       builder    = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bakery> query      = builder.createQuery( Bakery.class );
        Root<Bakery>          root       = query.from( Bakery.class );
        
        //後でListから配列へ変換,複数条件検索に対応予定
        List<Predicate>       predicates = new ArrayList<>();
        
        //名前検索
        String name = "";
        if( bakeryQuery.getName().length() > 0 ) {
            name = "%" + bakeryQuery.getName() + "%";
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
        TypedQuery<Bakery> typedQuery = entityManager.createQuery( query );
        
        //検索結果をListで取得
        List<Bakery> bakeryList = typedQuery.getResultList();
        
		return bakeryList;
		
	}
	

	
}
