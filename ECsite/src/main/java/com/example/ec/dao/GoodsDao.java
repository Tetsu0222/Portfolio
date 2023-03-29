package com.example.ec.dao;

import java.util.List;

import com.example.ec.entity.Goods;
import com.example.ec.form.GoodsQuery;

public interface GoodsDao {
	
	List<Goods> findByCriteria( GoodsQuery goodsQuery );

}
