package com.example.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.store.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	// tìm kiếm dựa trên giá trị truyền vào ( where x.name like ?1 (parameter bound wrapped in %))
	 @Query("SELECT c FROM Category c WHERE c.category_name like ?1%")
	List<Category> findByNameContaining(String category_name);
	
}
