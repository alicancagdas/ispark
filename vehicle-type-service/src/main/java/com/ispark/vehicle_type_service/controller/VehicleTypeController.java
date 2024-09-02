package com.ispark.vehicle_type_service.controller;

import com.ispark.vehicle_type_service.model.VehicleType;
import com.ispark.vehicle_type_service.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService service;

    @GetMapping
    public ResponseEntity<List<VehicleType>> getAllVehicleTypes() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{typeNo}")
    public ResponseEntity<VehicleType> getVehicleTypeByTypeNo(@PathVariable String typeNo) {
        return ResponseEntity.ok(service.findByTypeNo(typeNo));
    }

    @PostMapping
    public ResponseEntity<VehicleType> createOrUpdateVehicleType(@RequestBody VehicleType vehicleType) {
        VehicleType updated = service.saveOrUpdateVehicleType(vehicleType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{typeNo}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable String typeNo) {
        service.deleteByTypeNo(typeNo);
        return ResponseEntity.ok().build();
    }
}
