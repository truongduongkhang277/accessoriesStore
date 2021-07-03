package com.example.store.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		System.out.println(categoryDto);
		System.out.println(entity);
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
			@RequestParam(name = "categoryname", required = false) String categoryname) {
		
		List<Category> list = null;
		
		// kiểm tra xem có dữ liệu truyền vào từ người dùng
		if(StringUtils.hasText(categoryname)) {
			// truyền dữ liệu vào danh sách bằng findByNameContaining
			list = categoryService.findByCategorynameContaining(categoryname);
		} else {
			// nếu không thì hiển thị tất cả
			list = categoryService.findAll();
		}
		
		model.addAttribute("categories", list);
		
		return "admin/categories/search"; 
	}
	
	@GetMapping("searchPaginated")
	public String search(ModelMap model,
			@RequestParam(name = "categoryname", required = false) String categoryname,
			@RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size) {
		
		// mặc định trang đầu là 1
		int currentPage = page.orElse(1);
		
		// mặc định số lượng phần tử trong trang là 5
		int pageSize = size.orElse(5);
		
		// tạo đối tượng chứ trang, số lượng và sắp xếp theo thuộc tính gì
		// mặc định sắp xếp theo cate_id
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		// sắp xếp theo cate_name
		//Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryname"));
		Page<Category> resultPage = null; 
		
		// kiểm tra xem có dữ liệu truyền 
		if(StringUtils.hasText(categoryname)) {
			// truyền dữ liệu vào danh sách bằng findByNameContaining
			resultPage = categoryService.findByCategorynameContaining(categoryname, pageable);
			model.addAttribute("categoryname", categoryname);
		} else {
			// nếu không thì hiển thị tất cả
			resultPage = categoryService.findAll(pageable);
		}
		// tính toán số trang hiển thị điều hướng
		int totalPages = resultPage.getTotalPages();
		
		if(totalPages > 0) {
			// 1 2 3 4 5
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			// 1 2 3 4 5 6 7
			if(totalPages > 5 ) {
				// 3 4 5 6 7 8
				if(end == totalPages) {
					start = end - 5;
				} 
				// 1 2 3 4 5 6
				else if (start == 1) {
					end = start + 5;
				}
			}
			// 1 2 3 4 5, 5 6 7 8 9, ...
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		model.addAttribute("categoryPage", resultPage);
		
		return "admin/categories/searchPaginated"; 
	}

	@GetMapping("delete/{category_id}")
	public ModelAndView delete(ModelMap model, @PathVariable("category_id") Long category_id) {
		
		categoryService.deleteById(category_id);
		
		model.addAttribute("message", "Category is deleted !");
		
		return new ModelAndView("forword:/admin/categories", model);
	}
}
