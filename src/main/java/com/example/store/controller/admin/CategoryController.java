package com.example.store.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.store.model.CategoryDto;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/addOrEdit";
	}
	
	@GetMapping("edit/{categoryId}")
	public String edit() {
		return "admin/categories/addOrEdit";
	}
	
	@GetMapping("saveOrUpdate")
	public String saveOrUpdate() {
		return "redirect:/admin/categories";
	}
	
	@GetMapping("")
	public String list() {
		return "admin/categories/list";
	}
	
	@GetMapping("search")
	public String search() {
		return "admin/categories/search";
	}
	
	@GetMapping("delete/{categoryId}")
	public String delete() {
		return "redirect:/admin/categories";
	}
}
