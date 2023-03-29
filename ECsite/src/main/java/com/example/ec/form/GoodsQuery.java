package com.example.ec.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoodsQuery {
	
	@NotBlank
	private String  name;
	

}
