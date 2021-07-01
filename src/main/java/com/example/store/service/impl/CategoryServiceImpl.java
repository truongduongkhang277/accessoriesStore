package com.example.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.store.domain.Category;
import com.example.store.repository.CategoryRepository;
import com.example.store.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	CategoryRepository categoryReponsitory;

	public CategoryServiceImpl(CategoryRepository categoryReponsitory) {
		super();
		this.categoryReponsitory = categoryReponsitory;
	}

	@Override
	public List<Category> findByNameContaining(String category_name) {
		return categoryReponsitory.findByNameContaining(category_name);
	}

	@Override
	public <S extends Category> S save(S entity) {
		return categoryReponsitory.save(entity);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryReponsitory.findAll(pageable);
	}

	@Override
	public List<Category> findAll() {
		return categoryReponsitory.findAll();
	}

	@Override
	public List<Category> findAll(Sort sort) {
		return categoryReponsitory.findAll(sort);
	}

	@Override
	public List<Category> findAllById(Iterable<Long> ids) {
		return categoryReponsitory.findAllById(ids);
	}

	@Override
	public Optional<Category> findById(Long id) {
		return categoryReponsitory.findById(id);
	}

	@Override
	public <S extends Category> List<S> saveAll(Iterable<S> entities) {
		return categoryReponsitory.saveAll(entities);
	}

	@Override
	public void flush() {
		categoryReponsitory.flush();
	}

	@Override
	public <S extends Category> S saveAndFlush(S entity) {
		return categoryReponsitory.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return categoryReponsitory.existsById(id);
	}

	@Override
	public <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities) {
		return categoryReponsitory.saveAllAndFlush(entities);
	}

	@Override
	public void deleteInBatch(Iterable<Category> entities) {
		categoryReponsitory.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch(Iterable<Category> entities) {
		categoryReponsitory.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return categoryReponsitory.count();
	}

	@Override
	public void deleteById(Long id) {
		categoryReponsitory.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		categoryReponsitory.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Category entity) {
		categoryReponsitory.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		categoryReponsitory.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		categoryReponsitory.deleteAllInBatch();
	}

	@Override
	public Category getOne(Long id) {
		return categoryReponsitory.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Category> entities) {
		categoryReponsitory.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		categoryReponsitory.deleteAll();
	}

	@Override
	public Category getById(Long id) {
		return categoryReponsitory.getById(id);
	}

}
