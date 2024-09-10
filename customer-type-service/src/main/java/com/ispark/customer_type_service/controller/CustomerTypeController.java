package com.ispark.customer_type_service.controller;

import com.ispark.customer_type_service.model.CustomerType;
import com.ispark.customer_type_service.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Allows all origins
@RestController
@RequestMapping("/api/customer-types")
public class CustomerTypeController {

    @Autowired
    private CustomerTypeService service;

    // Get all customer types
    @GetMapping
    public ResponseEntity<List<CustomerType>> getAllCustomerTypes() {
        return ResponseEntity.ok(service.findAll());
    }

    // Get a customer type by customerTypeNo
    @GetMapping("/{customerTypeNo}")
    public ResponseEntity<CustomerType> getCustomerTypeByCustomerTypeNo(@PathVariable String customerTypeNo) {
        return ResponseEntity.ok(service.findByCustomerTypeNo(customerTypeNo));
    }

    // Create or update a customer type
    @PostMapping
    public ResponseEntity<CustomerType> createOrUpdateCustomerType(@RequestBody CustomerType customerType) {
        CustomerType updated = service.saveOrUpdateCustomerType(customerType);
        return ResponseEntity.ok(updated);
    }

    // Update a customer type by customerTypeNo
    @PutMapping("/{customerTypeNo}")
    public ResponseEntity<CustomerType> updateCustomerTypeByCustomerTypeNo(
            @PathVariable String customerTypeNo,
            @RequestBody CustomerType updatedCustomerType) {
        CustomerType updated = service.updateByCustomerTypeNo(customerTypeNo, updatedCustomerType);
        return ResponseEntity.ok(updated);
    }

    // Delete a customer type by customerTypeNo
    @DeleteMapping("/{customerTypeNo}")
    public ResponseEntity<Void> deleteCustomerType(@PathVariable String customerTypeNo) {
        service.deleteByCustomerTypeNo(customerTypeNo);
        return ResponseEntity.ok().build();
    }
}
