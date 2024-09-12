package com.ispark.upper_package_service.repository;

import com.ispark.upper_package_service.model.UpperPackage;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UpperPackageRepository extends MongoRepository<UpperPackage, String> {
    Optional<UpperPackage> findByUpperPackageNo(String upperPackageNo);
}
