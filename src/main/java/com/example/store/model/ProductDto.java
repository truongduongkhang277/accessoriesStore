package com.example.store.model;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long product_id;

	@NotEmpty
	private String productname;

	private int quantity;

	private double unitprice;

	private String image;

	private MultipartFile imageFile;
	
	private String description;

	private double discount;

	private Date entereddate;

	private short status;

	private Long category_id;
	
	private boolean is_edit;

	public boolean isIs_edit() {
		return is_edit;
	}

	public void setIs_edit(boolean is_edit) {
		this.is_edit = is_edit;
	}

}
