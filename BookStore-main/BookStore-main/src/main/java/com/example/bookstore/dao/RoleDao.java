/**
 * @(#)RoleDao.java 2021/08/24.
 * 
 * Copyright(C) 2021 by PHOENIX TEAM.
 * 
 * Last_Update 2021/08/24.
 * Version 1.00.
 */
package com.example.bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.UserRole;

import java.util.List;

/**
 * Class thuc hien truy van thong tin bang Role trong database
 * 
 * @author khoa-ph
 * @version 1.00
 */
public interface RoleDao extends JpaRepository<Role, Integer>{
	@Query("SELECT u.role.name FROM UserRole u WHERE u.user.id = :uid")
	List<String> getRoleNames(@Param("uid") Integer id);
	
	@Query("SELECT u FROM UserRole u WHERE u.user.id = :uid")
	UserRole getRoleByUserId(@Param("uid") Integer id);
}
