package com.example.store.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private int product_id;

	private String productname;

	private int quantity;

	private double unitprice;

	private String image;

	private String description;

	private double discount;

	private Date entereddate;

	private short status;

	private int category_id;

}
