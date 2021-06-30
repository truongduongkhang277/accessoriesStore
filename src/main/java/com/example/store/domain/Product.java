package com.example.store.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;

	@Column(columnDefinition = "varchar(150) not null")
	private String product_name;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double unit_price;

	@Column(length = 255)
	private String image;

	@Column(columnDefinition = "varchar(500) not null")
	private String description;

	@Column(nullable = false)
	private double discount;

	@Temporal(TemporalType.DATE)
	private Date entered_date;

	@Column(nullable = false)
	private short status;

	@Column(nullable = false)
	private int category_id;

}
