package com.ispark.upper_package_service.controller;

import com.ispark.upper_package_service.model.UpperPackage;
import com.ispark.upper_package_service.service.UpperPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
@CrossOrigin(origins = "*")  // Geliştirme sırasında tüm kökenlere izin ver
@RestController
@RequestMapping("/api/upper-packages")
public class UpperPackageController {

    @Autowired
    private UpperPackageService service;

    private static final Logger logger = LoggerFactory.getLogger(UpperPackageController.class);

    @PostMapping
    public ResponseEntity<UpperPackage> createUpperPackage(@RequestBody UpperPackage upperPackage) {
        logger.info("Creating new upper package: {}", upperPackage.getUpperPackageName());
        UpperPackage created = service.createUpperPackage(upperPackage);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<UpperPackage>> getAllUpperPackages() {
        logger.info("Retrieving all upper packages");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/no/{upperPackageNo}")
    public ResponseEntity<UpperPackage> getUpperPackageByNo(@PathVariable String upperPackageNo) {
        logger.info("Retrieving upper package by no: {}", upperPackageNo);
        UpperPackage found = service.findByUpperPackageNo(upperPackageNo);
        return ResponseEntity.ok(found);
    }

    @PutMapping("/no/{upperPackageNo}")
    public ResponseEntity<UpperPackage> updateUpperPackageByNo(@PathVariable String upperPackageNo, @RequestBody UpperPackage upperPackage) {
        logger.info("Updating upper package no: {}", upperPackageNo);
        UpperPackage updated = service.updateByUpperPackageNo(upperPackageNo, upperPackage);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/no/{upperPackageNo}")
    public ResponseEntity<Void> deleteUpperPackageByNo(@PathVariable String upperPackageNo) {
        logger.info("Deleting upper package no: {}", upperPackageNo);
        service.deleteByUpperPackageNo(upperPackageNo);
        return ResponseEntity.ok().build();
    }
}
