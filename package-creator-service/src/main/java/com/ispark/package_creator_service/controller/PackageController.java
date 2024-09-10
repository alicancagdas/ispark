package com.ispark.package_creator_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/by-customer-type-no")
    public ResponseEntity<Package> getPackageByCustomerTypeNo(@RequestParam String customerTypeNo) {
        Optional<Package> optionalPackage = packageService.findPackageByCustomerTypeNo(customerTypeNo);
        return optionalPackage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
