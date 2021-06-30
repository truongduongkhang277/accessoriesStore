package com.example.store.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private int order_id;

	private Date order_date;

	private int customer_id;

	private double amount;

	private short status;

}
