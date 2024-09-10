package com.ispark.vehicle_type_service.service;

import com.ispark.vehicle_type_service.model.VehicleType;
import com.ispark.vehicle_type_service.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class VehicleTypeService {

    @Autowired
    private VehicleTypeRepository repository;

    // Logger to help with debugging and error tracing
    private static final Logger LOGGER = Logger.getLogger(VehicleTypeService.class.getName());

    // Retrieve all vehicle types
    public List<VehicleType> findAll() {
        return repository.findAll();
    }

    // Retrieve a vehicle type by type number
    public VehicleType findByTypeNo(String typeNo) {
        return repository.findByTypeNo(typeNo)
                .orElseThrow(() -> new NoSuchElementException("Vehicle type not found with typeNo: " + typeNo));
    }

    // Save or update a vehicle type
    public VehicleType saveOrUpdateVehicleType(VehicleType vehicleType) {
        try {
            return repository.save(vehicleType);
        } catch (Exception e) {
            LOGGER.severe("Error saving or updating vehicle type: " + e.getMessage());
            throw new RuntimeException("Failed to save or update vehicle type", e);
        }
    }

    // Delete a vehicle type by its type number
    public void deleteByTypeNo(String typeNo) {
        try {
            // Check if the vehicle type exists before deleting
            VehicleType vehicleType = repository.findByTypeNo(typeNo)
                    .orElseThrow(() -> new NoSuchElementException("Vehicle type not found with typeNo: " + typeNo));

            repository.delete(vehicleType);
        } catch (NoSuchElementException e) {
            LOGGER.warning("Attempted to delete non-existing vehicle type with typeNo: " + typeNo);
            throw e; // Re-throw to handle at the controller level
        } catch (Exception e) {
            LOGGER.severe("Error deleting vehicle type with typeNo: " + typeNo + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete vehicle type", e);
        }
    }

    // Update vehicle type by type number
    public VehicleType updateByTypeNo(String typeNo, VehicleType updatedVehicleType) {
        try {
            // Find the existing vehicle type by type number
            VehicleType existingVehicleType = repository.findByTypeNo(typeNo)
                    .orElseThrow(() -> new NoSuchElementException("Vehicle type not found with typeNo: " + typeNo));

            // Update the fields of the existing vehicle type
            existingVehicleType.setName(updatedVehicleType.getName());

            // Save the updated vehicle type
            return repository.save(existingVehicleType);
        } catch (NoSuchElementException e) {
            LOGGER.warning("Vehicle type with typeNo: " + typeNo + " not found for update");
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error updating vehicle type with typeNo: " + typeNo + ": " + e.getMessage());
            throw new RuntimeException("Failed to update vehicle type", e);
        }
    }
}
