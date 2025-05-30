package com.example.bookstore.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.bookstore.common.Constants;
import com.example.bookstore.entity.Address;
import com.example.bookstore.service.AddressService;

import java.util.List;

@Controller
public class DiaChiController {
	@Autowired
	AddressService addressService;
	
	
	@GetMapping("/account/address")
	public String index() {
		return Constants.USER_DISPLAY_ACCOUNT_ADDRESS;
	}
	
	@GetMapping("/account/address/add")
	public String add(Model model) {
		model.addAttribute("enableBtnUpdate", false);
		return Constants.USER_DISPLAY_ACCOUNT_ADDRESS_ADD;
	}
	
	@GetMapping("/account/address/delete/{id}")
	public String add(@PathVariable("id") int id) {
		Address address = addressService.getAddressById(id);
		
		addressService.delete(address);
		
		return "redirect:/account/address";
	}
	
	@GetMapping("/account/address/update/{id}")
	public String update(@PathVariable("id") int id, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		Address address = addressService.findAddressById(username, id);
		
		if(address == null) {
			return "redirect:/account/address";
		}
		
		model.addAttribute("addressId", id);
		model.addAttribute("enableBtnUpdate", true);
		return Constants.USER_DISPLAY_ACCOUNT_ADDRESS_ADD;
	}
	
	@ModelAttribute("listAddress")
	public List<Address> getListAddress(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		return addressService.findListAddressByEmail(username);
	}
}
