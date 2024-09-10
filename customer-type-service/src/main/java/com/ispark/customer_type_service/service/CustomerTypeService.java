package com.ispark.customer_type_service.service;

import com.ispark.customer_type_service.model.CustomerType;
import com.ispark.customer_type_service.repository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerTypeService {

    @Autowired
    private CustomerTypeRepository repository;

    // Retrieve all customer types
    public List<CustomerType> findAll() {
        return repository.findAll();
    }

    // Retrieve a customer type by customerTypeNo
    public CustomerType findByCustomerTypeNo(String customerTypeNo) {
        return repository.findByCustomerTypeNo(customerTypeNo)
                .orElseThrow(() -> new NoSuchElementException("Customer type not found with customerTypeNo: " + customerTypeNo));
    }

    // Save or update a customer type
    public CustomerType saveOrUpdateCustomerType(CustomerType customerType) {
        return repository.save(customerType);
    }

    // Delete a customer type by customerTypeNo
    public void deleteByCustomerTypeNo(String customerTypeNo) {
        try {
            CustomerType customerType = repository.findByCustomerTypeNo(customerTypeNo)
                    .orElseThrow(() -> new NoSuchElementException("Customer type not found with customerTypeNo: " + customerTypeNo));
            repository.delete(customerType);
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error deleting customer type: " + e.getMessage());
            throw new RuntimeException("Failed to delete customer type: " + customerTypeNo, e);
        }
    }

    // Update customer type by customerTypeNo
    public CustomerType updateByCustomerTypeNo(String customerTypeNo, CustomerType updatedCustomerType) {
        try {
            CustomerType existingCustomerType = repository.findByCustomerTypeNo(customerTypeNo)
                    .orElseThrow(() -> new NoSuchElementException("Customer type not found with customerTypeNo: " + customerTypeNo));

            // Update the fields of the existing customer type
            existingCustomerType.setName(updatedCustomerType.getName());
            // Add other fields to update if necessary

            return repository.save(existingCustomerType);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error updating customer type: " + e.getMessage());
            throw new RuntimeException("Failed to update customer type: " + customerTypeNo, e);
        }
    }
}
