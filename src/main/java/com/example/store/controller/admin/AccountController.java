package com.example.store.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.store.domain.Account;
import com.example.store.model.AccountDto;
import com.example.store.service.AccountService;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/addOrEdit";
	}

	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {

		Optional<Account> opt = accountService.findById(username);

		AccountDto accountDto = new AccountDto();

		// có dữ liệu
		if (opt.isPresent()) {
			// lấy thông tin của entity
			Account entity = opt.get();
			// copy dữ liệu của entity sang dto
			BeanUtils.copyProperties(entity, accountDto);
			// nếu chỉnh sửa thì đổi isEdit thành true;
			accountDto.setIs_edit(true);
			accountDto.setPassword("");
			model.addAttribute("account", accountDto);
			return new ModelAndView("admin/accounts/addOrEdit", model);
		}

		model.addAttribute("message", "account is not existed");

		return new ModelAndView("forward:/admin/accounts", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("account") AccountDto accountDto, BindingResult result) {
		// kiểm tra dữ liệu người dùng nhập
		if (result.hasErrors()) {
			// nếu có lỗi thì quay lại trang addoredit
			return new ModelAndView("admin/accounts/addOrEdit");
		}

		Account entity = new Account();
		// copy dữ liệu từ dto sang entity
		BeanUtils.copyProperties(accountDto, entity);
		accountService.save(entity);
		System.out.println(entity);
		model.addAttribute("message", "Account is saved !");

		return new ModelAndView("redirect:/admin/accounts", model);
	}

	@GetMapping("")
	public String list(ModelMap model) {
		// tìm các accounts đang có
		List<Account> list = accountService.findAll();
		// thiết lập thuộc tính cho model
		model.addAttribute("accounts", list);

		return "admin/accounts/list";
	}

//	@GetMapping("search")
//	public String search(ModelMap model,
//			@RequestParam(name = "accountname", required = false) String accountname) {
//		
//		List<Account> list = null;
//		
//		// kiểm tra xem có dữ liệu truyền vào từ người dùng
//		if(StringUtils.hasText(accountname)) {
//			// truyền dữ liệu vào danh sách bằng findByNameContaining
//			//list = accountService.findByaccountnameContaining(accountname);
//		} else {
//			// nếu không thì hiển thị tất cả
//			list = accountService.findAll();
//		}
//		
//		model.addAttribute("accounts", list);
//		
//		return "admin/accounts/search"; 
//	}
//	
//	@GetMapping("searchPaginated")
//	public String search(ModelMap model,
//			@RequestParam(name = "accountname", required = false) String accountname,
//			@RequestParam(name = "page") Optional<Integer> page,
//			@RequestParam(name = "size") Optional<Integer> size) {
//		
//		// mặc định trang đầu là 1
//		int currentPage = page.orElse(1);
//		
//		// mặc định số lượng phần tử trong trang là 5
//		int pageSize = size.orElse(5);
//		
//		// tạo đối tượng chứ trang, số lượng và sắp xếp theo thuộc tính gì
//		// mặc định sắp xếp theo cate_id
//		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
//		// sắp xếp theo cate_name
//		//Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("accountname"));
//		Page<Account> resultPage = null; 
//		
//		// kiểm tra xem có dữ liệu truyền 
//		if(StringUtils.hasText(accountname)) {
//			// truyền dữ liệu vào danh sách bằng findByNameContaining
//			//resultPage = accountService.findByaccountnameContaining(accountname, pageable);
//			model.addAttribute("accountname", accountname);
//		} else {
//			// nếu không thì hiển thị tất cả
//			resultPage = accountService.findAll(pageable);
//		}
//		// tính toán số trang hiển thị điều hướng
//		int totalPages = resultPage.getTotalPages();
//		
//		if(totalPages > 0) {
//			// 1 2 3 4 5
//			int start = Math.max(1, currentPage - 2);
//			int end = Math.min(currentPage + 2, totalPages);
//			// 1 2 3 4 5 6 7
//			if(totalPages > 5 ) {
//				// 3 4 5 6 7 8
//				if(end == totalPages) {
//					start = end - 5;
//				} 
//				// 1 2 3 4 5 6
//				else if (start == 1) {
//					end = start + 5;
//				}
//			}
//			// 1 2 3 4 5, 5 6 7 8 9, ...
//			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
//			
//			model.addAttribute("pageNumbers", pageNumbers);
//		}
//		
//		model.addAttribute("accountPage", resultPage);
//		
//		return "admin/accounts/searchPaginated"; 
//	}

	@GetMapping("delete/{account_id}")
	public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {
		
		accountService.deleteById(username);
		
		model.addAttribute("message", "Account is deleted !");
		
		return new ModelAndView("forword:/admin/accounts", model);
	}
}
