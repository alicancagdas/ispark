package com.ispark.customer_type_service.controller;

import com.ispark.customer_type_service.model.CustomerType;
import com.ispark.customer_type_service.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/customer-types")
public class CustomerTypeController {

    @Autowired
    private CustomerTypeService service;

    @GetMapping
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{customerTypeNo}")
    public ResponseEntity<CustomerType> getCustomerTypeByCustomerTypeNo(@PathVariable String customerTypeNo) {
        return ResponseEntity.ok(service.findByCustomerTypeNo(customerTypeNo));
    }

    @PostMapping
    public ResponseEntity<CustomerType> createOrUpdateCustomerType(@RequestBody CustomerType customerType) {
        CustomerType updated = service.saveOrUpdateCustomerType(customerType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{customerTypeNo}")
    public ResponseEntity<Void> deleteCustomerType(@PathVariable String customerTypeNo) {
        service.deleteByCustomerTypeNo(customerTypeNo);
        return ResponseEntity.ok().build();
    }
}
