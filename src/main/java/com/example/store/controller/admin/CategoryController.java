package com.example.store.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.store.domain.Category;
import com.example.store.model.CategoryDto;
import com.example.store.service.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/addOrEdit";
	}

	@GetMapping("edit/{category_id}")
	public ModelAndView edit(ModelMap model, @PathVariable("category_id") Long category_id) {

		Optional<Category> opt = categoryService.findById(category_id);

		CategoryDto categoryDto = new CategoryDto();

		// có dữ liệu
		if (opt.isPresent()) {
			// lấy thông tin của entity
			Category entity = opt.get();
			// copy dữ liệu của entity sang dto
			BeanUtils.copyProperties(entity, categoryDto);
			// nếu chỉnh sửa thì đổi isEdit thành true;
			categoryDto.setIs_edit(true);

			model.addAttribute("category", categoryDto);
			return new ModelAndView("admin/categories/addOrEdit", model);
		}

		model.addAttribute("message", "Category is not existed");

		return new ModelAndView("forward:/admin/categories", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryDto categoryDto,
			BindingResult result) {
		// kiểm tra dữ liệu người dùng nhập
		if (result.hasErrors()) {
			// nếu có lỗi thì quay lại trang addoredit
			return new ModelAndView("admin/categories/addOrEdit");
		}

		Category entity = new Category();
		// copy dữ liệu từ dto sang entity
		BeanUtils.copyProperties(categoryDto, entity);
		categoryService.save(entity);

		model.addAttribute("message", "Category is saved !");

		return new ModelAndView("redirect:/admin/categories", model);
	}

	@GetMapping("")
	public String list(ModelMap model) {
		// tìm các categories đang có
		List<Category> list = categoryService.findAll();
		// thiết lập thuộc tính cho model
		model.addAttribute("categories", list);

		return "admin/categories/list";
	}

	@GetMapping("search")
	public String search(ModelMap model,
			@RequestParam(name = "category_name", required = false) String category_name) {
		
		List<Category> list = null;
		
		// kiểm tra xem có dữ liệu truyền vào từ người dùng
		if(StringUtils.hasText(category_name)) {
			// truyền dữ liệu vào danh sách bằng findByNameContaining
			list = categoryService.findByNameContaining(category_name);
		} else {
			// nếu không thì hiển thị tất cả
			list = categoryService.findAll();
		}
		
		model.addAttribute("categories", list);
		
		return "admin/categories/search"; 
	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("category_id") Long category_id) {
		
		categoryService.deleteById(category_id);
		
		model.addAttribute("message", "Category is deleted !");
		
		return new ModelAndView("forword:/admin/categories", model);
	}
}
