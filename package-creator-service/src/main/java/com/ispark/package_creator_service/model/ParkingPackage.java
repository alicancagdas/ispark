package com.ispark.package_creator_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingPackage { // Paket sınıfının ismini değiştiriyoruz
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String packageId;

    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private String customerTypeNo;

    @Column(nullable = false)
    private String customerTypeName;

    @ElementCollection
    private List<VehicleTypeWithTariff> vehicleTypes;
}
