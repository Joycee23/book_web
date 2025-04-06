package com.example.bookstore.controller;

import com.example.bookstore.service.DataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataMigrationController {

    @Autowired
    private DataMigrationService dataMigrationService;

    @GetMapping("/migrate")
    public String migrateData() {
        dataMigrationService.migrateAllTables();
        return "Data migration started.";
    }
}