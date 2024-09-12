package com.ispark.upper_package_service.service;

import com.ispark.upper_package_service.model.UpperPackage;
import com.ispark.upper_package_service.repository.UpperPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UpperPackageService {

    @Autowired
    private UpperPackageRepository repository;

    // Üst paketi oluştur
    public UpperPackage createUpperPackage(UpperPackage upperPackage) {
        return repository.save(upperPackage);
    }

    // Tüm üst paketleri bul
    public List<UpperPackage> findAll() {
        return repository.findAll();
    }

    // upperPackageNo'ya göre üst paket bul
    public UpperPackage findByUpperPackageNo(String upperPackageNo) {
        return repository.findByUpperPackageNo(upperPackageNo)
                .orElseThrow(() -> new RuntimeException("Upper package not found with packageNo: " + upperPackageNo));
    }

    // upperPackageNo'ya göre üst paketi güncelle
    public UpperPackage updateByUpperPackageNo(String upperPackageNo, UpperPackage updatedUpperPackage) {
        UpperPackage packageToUpdate = findByUpperPackageNo(upperPackageNo);
        packageToUpdate.setUpperPackageName(updatedUpperPackage.getUpperPackageName());
        packageToUpdate.setUpperPackageDescription(updatedUpperPackage.getUpperPackageDescription());
        packageToUpdate.setUpperSubPackages(updatedUpperPackage.getUpperSubPackages());
        return repository.save(packageToUpdate);
    }

    // upperPackageNo'ya göre üst paketi sil
    public void deleteByUpperPackageNo(String upperPackageNo) {
        UpperPackage packageToDelete = findByUpperPackageNo(upperPackageNo);
        repository.delete(packageToDelete);
    }
}
