package com.example.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.store.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	// tìm kiếm dựa trên giá trị truyền vào ( where x.name like ?1 (parameter bound
		// wrapped in %))
		//@Query("SELECT c FROM Category c WHERE c.category_name like ?1%")
		List<Product> findByProductnameContaining(String productname);

		//@Query("SELECT c FROM Category c WHERE c.category_name like ?1%")
		Page<Product> findByProductnameContaining(String productname, Pageable pageable);

	
}
