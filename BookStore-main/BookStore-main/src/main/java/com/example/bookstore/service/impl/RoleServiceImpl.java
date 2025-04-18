/**
 * @(#)RoleServiceImpl.java 2021/09/09.
 * 
 * Copyright(C) 2021 by PHOENIX TEAM.
 * 
 * Last_Update 2021/09/09.
 * Version 1.00.
 */
package com.example.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bookstore.dao.RoleDao;
import com.example.bookstore.entity.Role;
import com.example.bookstore.service.RoleService;

import java.util.List;

/**
 * Class trien khai theo interface RoleService, Thao tac voi Class RoleDao de
 * thuc hien cac tac vu tuong ung
 * 
 * @author KHOA-PH
 * @version 1.00
 */
@Service
public class RoleServiceImpl implements RoleService {
	// Thong tin role dao
	@Autowired
	RoleDao roleDao;

	/**
	 * Tim kiem role theo id cua role
	 * 
	 * @param thong tin id cua role
	 * @return Role tim duoc
	 */
	@Override
	public Role findRoleById(int roleId) {
		return roleDao.findById(roleId).get();
	}
	
	/**
	 * Tim ten vai tro dua vao user id
	 * 
	 * @param thong tin user id
	 * @return danh sach ten vai tro tim duoc
	 */
	@Override
	public List<String> getRoleNames(int userId) {
		return roleDao.getRoleNames(userId);
	}

}
