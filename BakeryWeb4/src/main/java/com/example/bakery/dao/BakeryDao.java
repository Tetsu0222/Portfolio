package com.example.bakery.dao;

import java.util.List;

import com.example.bakery.entity.Bakery;
import com.example.bakery.form.BakeryQuery;

public interface BakeryDao {
	
	List<Bakery> findByCriteria( BakeryQuery bakeryQuery );

}
