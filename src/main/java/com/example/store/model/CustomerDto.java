package com.example.store.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	private int customer_id;

	private String customer_name;

	private String email;

	private String password;

	private String phone;

	private Date registered_date;

	private short status;

}
