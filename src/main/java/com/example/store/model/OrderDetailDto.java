package com.example.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

	private int order_detail_id;

	private int order_id;

	private int product_id;

	private int quantity;

	private double unitprice;

}
