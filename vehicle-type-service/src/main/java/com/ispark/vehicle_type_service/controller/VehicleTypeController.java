package com.ispark.vehicle_type_service.controller;

import com.ispark.vehicle_type_service.model.VehicleType;
import com.ispark.vehicle_type_service.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Allows all origins
@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService service;

    // Fetch all vehicle types
    @GetMapping
    public ResponseEntity<List<VehicleType>> getAllVehicleTypes() {
        return ResponseEntity.ok(service.findAll());
    }

    // Fetch a specific vehicle type by type number
    @GetMapping("/{typeNo}")
    public ResponseEntity<VehicleType> getVehicleTypeByTypeNo(@PathVariable String typeNo) {
        return ResponseEntity.ok(service.findByTypeNo(typeNo));
    }

    // Create or update a vehicle type
    @PostMapping
    public ResponseEntity<VehicleType> createOrUpdateVehicleType(@RequestBody VehicleType vehicleType) {
        VehicleType updated = service.saveOrUpdateVehicleType(vehicleType);
        return ResponseEntity.ok(updated);
    }

    // Delete a vehicle type by its type number
    @DeleteMapping("/{typeNo}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable String typeNo) {
        service.deleteByTypeNo(typeNo);
        return ResponseEntity.ok().build();
    }

    // Update vehicle type by type number
    @PutMapping("/{typeNo}")
    public ResponseEntity<VehicleType> updateVehicleType(@PathVariable String typeNo, @RequestBody VehicleType vehicleType) {
        VehicleType updatedVehicleType = service.updateByTypeNo(typeNo, vehicleType);
        return ResponseEntity.ok(updatedVehicleType);
    }
}
