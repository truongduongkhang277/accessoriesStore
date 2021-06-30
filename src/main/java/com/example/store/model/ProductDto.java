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

	private String product_name;

	private int quantity;

	private double unit_price;

	private String image;

	private String description;

	private double discount;

	private Date entered_date;

	private short status;

	private int category_id;

}
