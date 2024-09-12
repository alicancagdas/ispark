package com.ispark.package_creator_service.repository;

import com.ispark.package_creator_service.model.ParkingPackage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPackageRepository extends MongoRepository<ParkingPackage, String> {
    // Custom query methods (if any) can be defined here
}
