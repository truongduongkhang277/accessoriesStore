package com.example.store.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.model.CategoryDto;
import com.example.store.model.ProductDto;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import com.example.store.service.StorageService;


@Controller
@RequestMapping("admin/products")
public class ProductController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;
	
	@Autowired
	StorageService storageService;

	// ánh xạ cate_dto sang list -> lựa chọn khi thêm hoặc sửa sản phẩm
	@ModelAttribute("categories")
	public List<CategoryDto> getCategories(){
		return categoryService.findAll().stream().map(item-> {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	
	@GetMapping("add")
	public String add(Model model) {
		ProductDto productDto = new ProductDto();
		
		productDto.setIs_edit(false);
		
		model.addAttribute("product", productDto);
		
		return "admin/products/addOrEdit";
	}

	@GetMapping("edit/{product_id}")
	public ModelAndView edit(ModelMap model, @PathVariable("product_id") Long product_id) {

		Optional<Product> opt = productService.findById(product_id);

		ProductDto productDto = new ProductDto();

		// có dữ liệu
		if (opt.isPresent()) {
			// lấy thông tin của entity
			Product entity = opt.get();
			// copy dữ liệu của entity sang dto
			BeanUtils.copyProperties(entity, productDto);
			// nếu chỉnh sửa thì đổi isEdit thành true;
			
			productDto.setCategory_id(entity.getCategory().getCategory_id());
			
			productDto.setIs_edit(true);

			model.addAttribute("product", productDto);
			return new ModelAndView("admin/products/addOrEdit", model);
		}

		model.addAttribute("message", "Product is not existed");

		return new ModelAndView("forward:/admin/products", model);
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
	  Resource file = storageService.loadAsResource(filename);
		
		return ResponseEntity
			      .ok()
			      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
			      .body(file);
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto productDto,
			BindingResult result) {
		// kiểm tra dữ liệu người dùng nhập
		if (result.hasErrors()) {
			// nếu có lỗi thì quay lại trang addoredit
			return new ModelAndView("admin/products/addOrEdit");
		}

		Product entity = new Product();
		// copy dữ liệu từ dto sang entity
		BeanUtils.copyProperties(productDto, entity);
		
		Category category = new Category();
		category.setCategory_id(productDto.getCategory_id());
		entity.setCategory(category);
		
		if(!productDto.getImageFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			
			entity.setImage(storageService.getStoredFileName(productDto.getImageFile(), uuString));
			storageService.store(productDto.getImageFile(), entity.getImage());

		}	
		productService.save(entity);
		
		model.addAttribute("message", "Product is saved !");

		return new ModelAndView("redirect:/admin/products", model);
	}			

	@GetMapping("")
	public String list(ModelMap model) {
		// tìm các categories đang có
		List<Product> list = productService.findAll();
		// thiết lập thuộc tính cho model
		model.addAttribute("products", list);
		
		return "admin/products/list";
	}

	@GetMapping("search")
	public String search(ModelMap model,
			@RequestParam(name = "productname", required = false) String productname) {
		
		List<Product> list = null;
		
		// kiểm tra xem có dữ liệu truyền vào từ người dùng
		if(StringUtils.hasText(productname)) {
			// truyền dữ liệu vào danh sách bằng findByNameContaining
			//list = productService.findByProductnameContaining(productname);
		} else {
			// nếu không thì hiển thị tất cả
			list = productService.findAll();
		}
		
		model.addAttribute("products", list);
		
		return "admin/products/search"; 
	}

	@GetMapping("delete/{product_id}")
	public ModelAndView delete(ModelMap model, @PathVariable("product_id") Long product_id) throws IOException {
		
		Optional<Product> opt = productService.findById(product_id);
		
		if(opt.isPresent()) {
			if(!StringUtils.isEmpty(opt.get().getImage())) {
				storageService.delete(opt.get().getImage());
			}
			productService.delete(opt.get());

			model.addAttribute("message", "Product is deleted !");
		} else {

			model.addAttribute("message", "Product is not found !");
			
		}
		
		return new ModelAndView("forward:/admin/products", model);
	}
}
