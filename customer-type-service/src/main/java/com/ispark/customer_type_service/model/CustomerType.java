package com.ispark.customer_type_service.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "customer_types")
public class CustomerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String customerTypeNo;  // Unique identifier for the customer type

    @Column(nullable = false)
    private String name;  // e.g., Standard, Disabled, Police-Military
}
