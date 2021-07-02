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

	private String customername;

	private String email;

	private String password;

	private String phone;

	private Date registereddate;

	private short status;

}
