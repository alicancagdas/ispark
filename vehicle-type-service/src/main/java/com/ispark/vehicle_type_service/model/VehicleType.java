package com.ispark.vehicle_type_service.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "vehicle_types")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String typeNo;  // Unique identifier for vehicle type, e.g., "VT100", "VT101"

    @Column(nullable = false)
    private String name;  // e.g., Car, Motorcycle, Bus, Truck
}
