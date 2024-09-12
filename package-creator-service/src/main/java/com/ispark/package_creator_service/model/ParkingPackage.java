package com.ispark.package_creator_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "parkingPackages")
public class ParkingPackage {

    @Id
    private String id;
    private String packageName;          // Paket ismi
    private String packageDescription;   // Paket açıklaması
    private String customerTypeName;
    private String customerTypeNo;
    private List<VehicleType> vehicleTypes; // Araç tipleri tarifeleri içerir.

    @Data
    public static class VehicleType {
        private String vehicleTypeName;
        private String vehicleTypeNo;
        private List<Tariff> tariffs; // Her araç tipi kendi tarifelerini içerir.
    }

    @Data
    public static class Tariff {
        private String tariffNo;
        private String tariffName;
    }
}
