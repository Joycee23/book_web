package com.example.bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.bookstore.entity.Contact;

import java.util.List;

public interface ContactDao extends JpaRepository<Contact, Integer> {
//	@Query("SELECT c FROM C c WHERE c.status = 1 AND c.product.id = :uid")
//	List<Comment> getListCommentByProductId(@Param("uid") Integer id);
	
	@Query("SELECT c FROM Contact c WHERE c.status = 0")
	List<Contact> getListContactPending();
	
	@Query("SELECT c FROM Contact c WHERE c.status = 1")
	List<Contact> getListContactChecked();
	
	@Query("SELECT c FROM Contact c WHERE c.id = :uid")
	Contact getContactByContactId(@Param("uid") Integer id);
}
