package com.ispark.customer_type_service.repository;

import com.ispark.customer_type_service.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
    Optional<CustomerType> findByCustomerTypeNo(String customerTypeNo);
    void deleteByCustomerTypeNo(String customerTypeNo);
}
