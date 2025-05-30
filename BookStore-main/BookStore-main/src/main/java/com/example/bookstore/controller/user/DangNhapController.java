/**
 * @(#)LoginController.java 2021/09/09.
 * 
 * Copyright(C) 2021 by PHOENIX TEAM.
 * 
 * Last_Update 2021/09/09.
 * Version 1.00.
 */
package com.example.bookstore.controller.user;

import com.example.bookstore.user.LoginFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.bookstore.common.Constants;
import com.example.bookstore.model.UserLogin;
import com.example.bookstore.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Class su ly cac hoat dong dang nhap cua nguoi dung
 * 
 * @author khoa-ph
 * @version 1.00
 */
@Controller
public class DangNhapController {

	// Trinh bat loi form
	@Autowired
	LoginFormValidator loginFormValidator;

	// Cung cap cac dich vu cho lop user
	@Autowired
	UserService userService;

	/**
	 * Rang buoc form voi trinh bat loi
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == UserLogin.class) {
			binder.setValidator(loginFormValidator);
		}
	}

	/**
	 * Hien thi trang login
	 * 
	 * @return trang login.html
	 */
	@GetMapping("/login")
	public String login(Model model) {
		// Rang buoc form ten userLogin voi model UserLogin.java
		UserLogin userLogin = new UserLogin();
		model.addAttribute("userLogin", userLogin);

		// Hien thi trang login.html
		return Constants.USER_DISPLAY_LOGIN;
	}
	
	/**
	 * Xu ly qua trinh login
	 * 
	 * @param model
	 * @param userLogin
	 * @param result
	 * @return Man hinh login neu co loi. Nguoc lai quay lai trang chu
	 */
	@PostMapping("/login")
	public String handlerLoginForm(Model model, @ModelAttribute("userLogin") @Validated UserLogin userLogin,
			BindingResult result) {
		// Neu co loi se quay tro lai trang login
		if (result.hasErrors()) {
			return Constants.USER_DISPLAY_LOGIN;
		}

		// Hien thi trang chu nguoi dung
		return Constants.USER_DISPLAY_INDEX;
	}

	/**
	 * Xu ly khi nguoi dung dang nhap thanh cong
	 *
	 * @param model  doi tuong Model de truyen du lieu sang view
	 * @param request doi tuong HttpServletRequest chua thong tin request
	 * @param principal doi tuong Principal chua thong tin nguoi dung dang nhap
	 * @return man hinh trang chu
	 */
	@GetMapping("/login/success")
	public String loginSuccess(Model model, HttpServletRequest request, Principal principal) {
		// Hien thi trang chu nguoi dung
		return "redirect:/index";
	}

}
