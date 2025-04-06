package com.example.bookstore.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class DataMigrationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void migrateAllTables() {
        Firestore db = FirestoreClient.getFirestore();

        // Migrate addresses
        migrateTable(db, "addresses", "address");
        // Migrate blogs
        migrateTable(db, "blogs", "Blogs");
        // Migrate categories
        migrateTable(db, "categories", "Categories");
        // Migrate comments
        migrateTable(db, "comments", "comments");
        // Migrate contacts
        migrateTable(db, "contacts", "Contacts");
        // Migrate discount
        migrateTable(db, "discounts", "Discount");
        // Migrate employee_model
        migrateTable(db, "employeeModels", "employee_model");
        // Migrate employees
        migrateTable(db, "employees", "employees");
        // Migrate favorites
        migrateTable(db, "favorites", "favorites");
        // Migrate information_shop
        migrateTable(db, "informationShops", "InformationShop");
        // Migrate manufactures
        migrateTable(db, "manufacturers", "Manufactures");
        // Migrate menuone
        migrateTable(db, "menuOnes", "menuone");
        // Migrate menutwo
        migrateTable(db, "menuTwos", "menutwo");
        // Migrate order_model
        migrateTable(db, "orderModels", "order_model");
        // Migrate orders
        migrateTable(db, "orders", "orders");
        // Migrate products
        migrateTable(db, "products", "products");
        // Migrate roles
        migrateTable(db, "roles", "Roles");
        // Migrate statistical_order
        migrateTable(db, "statisticalOrders", "statistical_order");
        // Migrate statistical_produ...
        migrateTable(db, "statisticalProducts", "statistical_produ...");
        // Migrate user_role
        migrateTable(db, "userRoles", "user_role");
    }

    private void migrateTable(Firestore db, String collectionName, String tableName) {
        List<Map<String, Object>> records = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
        for (Map<String, Object> record : records) {
            String recordId = String.valueOf(record.get("Id")); // Giả sử cột Id là khóa chính
            db.collection(collectionName).document(recordId).set(record);
        }
    }
}
