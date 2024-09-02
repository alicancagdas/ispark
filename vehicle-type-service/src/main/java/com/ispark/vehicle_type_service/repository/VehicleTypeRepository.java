package com.ispark.vehicle_type_service.repository;

import com.ispark.vehicle_type_service.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByTypeNo(String typeNo);
    void deleteByTypeNo(String typeNo);
}
