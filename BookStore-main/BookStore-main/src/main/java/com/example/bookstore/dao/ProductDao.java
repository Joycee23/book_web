package com.example.bookstore.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.bookstore.entity.Product;

import java.util.List;

/**
 * Class thực hiện truy vấn thông tin bảng Product trong database
 *
 * @author KHOA-PH
 * @version 1.00
 */
public interface ProductDao extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.Deleteday IS NULL")
	List<Product> getListProduct();

	@Query(value = "SELECT * FROM Products WHERE DeleteDay IS NULL AND Active = 1 ORDER BY Createday DESC LIMIT 16", nativeQuery = true)
	List<Product> getListLatestProduct();

	@Query(value = "SELECT * FROM Products WHERE DeleteDay IS NULL AND Active = 1 ORDER BY Views DESC LIMIT 14", nativeQuery = true)
	List<Product> getListViewsProduct();

	@Query(value = "SELECT * FROM products WHERE DeleteDay IS NULL AND Active = 1 AND Namesearch LIKE CONCAT('%', ?1, '%') ORDER BY Createday DESC LIMIT 16", nativeQuery = true)
	Page<Product> getListProductByNameSearch(String nameSearch, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.category IS NOT NULL AND p.category.Namesearch LIKE CONCAT('%', ?1, '%') AND p.Deleteday IS NULL AND p.active = 1 AND p.price BETWEEN ?2 AND ?3 ORDER BY p.Createday DESC")
	Page<Product> getListProductByPrice(String nameSearch, int minPrice, int maxPrice, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.category IS NOT NULL AND p.category.Namesearch LIKE CONCAT('%', ?1, '%') AND p.Deleteday IS NULL")
	List<Product> getListDemo(String nameSearch);

	@Query("SELECT p FROM Product p WHERE p.Deleteday IS NULL AND p.Namesearch LIKE CONCAT('%', ?1, '%')")
	Product getProductByNameSearch(String nameSearch);

	@Query(value = "SELECT * FROM Products WHERE DeleteDay IS NULL AND Active = 1 AND Cate_Id = ?1 ORDER BY Views DESC LIMIT 10", nativeQuery = true)
	List<Product> getListProductRelated(int manuId);

	@Query(value = "SELECT * FROM Products WHERE DeleteDay IS NULL AND Active = 1 AND Sales != 0 ORDER BY Views DESC LIMIT 5", nativeQuery = true)
	List<Product> getListProductSales();

	@Query(value = "SELECT * FROM Products WHERE NOT EXISTS (SELECT * FROM Orders WHERE Products.Id = Orders.Product_Id) AND Products.DeleteDay IS NULL", nativeQuery = true)
	List<Product> listStatisticalProductWarehouse();
}