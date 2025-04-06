package com.example.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.bookstore.dao.InformationShopDao;
import com.example.bookstore.dao.UserDao;
import com.example.bookstore.entity.InformationShop;
import com.example.bookstore.entity.User;
import com.example.bookstore.model.ShopModel;
import com.example.bookstore.service.InformationShopService;

import java.sql.Timestamp;
import java.util.List;

@Service
public class InformationShopServiceImpl implements InformationShopService{
	@Autowired
	UserDao userDao;
	
	@Autowired
	InformationShopDao informationDao;
	
	@Override
	public ShopModel createInformationShop(ShopModel shopModel) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User temp = userDao.findUserByEmail(username);
		
		InformationShop informationShop = new InformationShop();
		informationShop.setName(shopModel.getName());
		informationShop.setTimeopen(shopModel.getTime());
		informationShop.setLogo(shopModel.getLogo());
		informationShop.setLogofooter(shopModel.getLogoFooter());
		informationShop.setPhone(shopModel.getPhone());
		informationShop.setFax(shopModel.getFax());
		informationShop.setEmail(shopModel.getEmail());
		informationShop.setAddress(shopModel.getAddress());
		informationShop.setCreateday(timestamp.toString());
		informationShop.setPersoncreate(temp.getId());
		informationDao.save(informationShop);
		return shopModel;
	}

	@Override
	public List<InformationShop> findAll() {
		return informationDao.getListInformationShop();
	}

	@Override
	public void delete(Integer id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User temp = userDao.findUserByEmail(username);
		
		InformationShop informationShop = informationDao.findById(id).get();
		informationShop.setDeleteday(timestamp.toString());
		informationShop.setPersondelete(temp.getId());
		informationDao.save(informationShop);
	}

	@Override
	public ShopModel updateActive(ShopModel shopModel) {
		List<InformationShop> list = informationDao.findAll();
		for(InformationShop infor: list) {
			if(infor.getId() != shopModel.getId()) {
				infor.setActive(false);
			}
			else {
				infor.setActive(true);			
			}
			informationDao.save(infor);
		}
	
		return shopModel;
	}

	@Override
	public ShopModel getOneShopById(Integer id) {
		InformationShop informationShop = informationDao.findById(id).get();
		ShopModel shopModel = new ShopModel();
		shopModel.setAddress(informationShop.getAddress());
		shopModel.setEmail(informationShop.getEmail());
		shopModel.setFax(informationShop.getFax());
		shopModel.setPhone(informationShop.getPhone());
		shopModel.setLogo(informationShop.getLogo());
		shopModel.setLogoFooter(informationShop.getLogofooter());
		shopModel.setName(informationShop.getName());
		shopModel.setTime(informationShop.getTimeopen());
		return shopModel;
	}

	@Override
	public ShopModel updateInformation(ShopModel shopModel) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User temp = userDao.findUserByEmail(username);
		
		InformationShop informationShop = informationDao.findById(shopModel.getId()).get();
		informationShop.setAddress(shopModel.getAddress());
		informationShop.setEmail(shopModel.getEmail());
		informationShop.setFax(shopModel.getFax());
		informationShop.setLogo(shopModel.getLogo());
		informationShop.setLogofooter(shopModel.getLogoFooter());
		informationShop.setName(shopModel.getName());
		informationShop.setTimeopen(shopModel.getTime());
		informationShop.setPhone(shopModel.getPhone());
		informationShop.setUpdateday(timestamp.toString());
		informationShop.setPersonupdate(temp.getId());
		informationDao.save(informationShop);
		return shopModel;
	}

	@Override
	public InformationShop getOneInformationShop() {
		InformationShop informationShop = informationDao.getOneInformationShop();

		if (informationShop == null) {
			throw new RuntimeException("Không tìm thấy thông tin cửa hàng!");
		}

		if (informationShop.getPhone() != null && informationShop.getPhone().length() >= 10) {
			String phone = informationShop.getPhone();
			phone = phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
			informationShop.setPhone(phone);
		}

		if (informationShop.getFax() != null && informationShop.getFax().length() >= 10) {
			String fax = informationShop.getFax();
			fax = "+82 " + fax.substring(1, 4) + " " + fax.substring(4, 7) + " " + fax.substring(7);
			informationShop.setFax(fax);
		}

		return informationShop;
	}


}
