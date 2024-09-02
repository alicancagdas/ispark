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

    public List<CustomerType> findAll() {
        return repository.findAll();
    }

    public CustomerType findByCustomerTypeNo(String customerTypeNo) {
        return repository.findByCustomerTypeNo(customerTypeNo)
                .orElseThrow(() -> new NoSuchElementException("Customer type not found with customerTypeNo: " + customerTypeNo));
    }

    public CustomerType saveOrUpdateCustomerType(CustomerType customerType) {
        return repository.save(customerType);
    }

    public void deleteByCustomerTypeNo(String customerTypeNo) {
        repository.deleteByCustomerTypeNo(customerTypeNo);
    }
}
