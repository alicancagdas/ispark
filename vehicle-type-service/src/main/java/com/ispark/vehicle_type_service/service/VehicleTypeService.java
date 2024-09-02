package com.ispark.vehicle_type_service.service;

import com.ispark.vehicle_type_service.model.VehicleType;
import com.ispark.vehicle_type_service.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehicleTypeService {

    @Autowired
    private VehicleTypeRepository repository;

    public List<VehicleType> findAll() {
        return repository.findAll();
    }

    public VehicleType findByTypeNo(String typeNo) {
        return repository.findByTypeNo(typeNo)
                .orElseThrow(() -> new NoSuchElementException("Vehicle type not found with typeNo: " + typeNo));
    }

    public VehicleType saveOrUpdateVehicleType(VehicleType vehicleType) {
        return repository.save(vehicleType);
    }

    public void deleteByTypeNo(String typeNo) {
        repository.deleteByTypeNo(typeNo);
    }
}
