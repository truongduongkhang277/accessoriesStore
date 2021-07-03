package com.example.store.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="category")
public class Category implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long category_id;
	
	@Column(name="categoryname", length = 100, nullable = false, columnDefinition = "nvarchar(100) not null")
	private String categoryname;
	
	// khi xóa cate, prod thuộc cate cũng được xóa theo
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)	
	@ToString.Exclude
	private Set<Product> products;
	
}
