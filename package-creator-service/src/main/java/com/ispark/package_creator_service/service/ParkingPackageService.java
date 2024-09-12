package com.ispark.package_creator_service.service;

import com.ispark.package_creator_service.model.ParkingPackage;
import com.ispark.package_creator_service.repository.ParkingPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingPackageService {

    @Autowired
    private ParkingPackageRepository parkingPackageRepository;

    public ParkingPackage createParkingPackage(ParkingPackage parkingPackage) {
        // Gerekirse iş kuralları burada uygulanabilir (örneğin validasyonlar)
        return parkingPackageRepository.save(parkingPackage);
    }

    public List<ParkingPackage> getAllParkingPackages() {
        return parkingPackageRepository.findAll();
    }

    public ParkingPackage getParkingPackageById(String id) {
        return parkingPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paket bulunamadı"));
    }

    public ParkingPackage updateParkingPackage(String id, ParkingPackage parkingPackage) {
        ParkingPackage existingPackage = getParkingPackageById(id);
        existingPackage.setPackageName(parkingPackage.getPackageName());
        existingPackage.setPackageDescription(parkingPackage.getPackageDescription());
        existingPackage.setCustomerTypeName(parkingPackage.getCustomerTypeName());
        existingPackage.setCustomerTypeNo(parkingPackage.getCustomerTypeNo());
        existingPackage.setVehicleTypes(parkingPackage.getVehicleTypes());
        return parkingPackageRepository.save(existingPackage);
    }

    public void deleteParkingPackage(String id) {
        ParkingPackage parkingPackage = getParkingPackageById(id);
        parkingPackageRepository.delete(parkingPackage);
    }

    public ParkingPackage duplicateParkingPackage(String id) {
        ParkingPackage originalPackage = getParkingPackageById(id);
        ParkingPackage newPackage = new ParkingPackage();
        newPackage.setPackageName(originalPackage.getPackageName() + " - Copy");
        newPackage.setPackageDescription(originalPackage.getPackageDescription());
        newPackage.setCustomerTypeName(originalPackage.getCustomerTypeName());
        newPackage.setCustomerTypeNo(originalPackage.getCustomerTypeNo());
        newPackage.setVehicleTypes(originalPackage.getVehicleTypes());
        return parkingPackageRepository.save(newPackage);
    }
}
