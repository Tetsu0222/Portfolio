package com.example.bakery.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BakeryQuery {
	
	@NotBlank( message = "商品名を入力してください" )
	private String name;

}
