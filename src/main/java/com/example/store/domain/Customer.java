package com.example.store.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private int customerId;
	private String customerName;
	private String email;
	private String password;
	private String phone;
	private Date registeredDate;
	private short status;
	
}
