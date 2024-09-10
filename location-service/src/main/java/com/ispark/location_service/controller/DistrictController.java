package com.ispark.location_service.controller;

import com.ispark.location_service.dto.DistrictDTO;
import com.ispark.location_service.exception.ResourceNotFoundException;
import com.ispark.location_service.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*") // Allows all origins
@RestController
@RequestMapping("/api/districts")
@Tag(name = "District", description = "Operations related to district management")
public class DistrictController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Get districts by city code", description = "Retrieve a list of districts by city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of districts"),
            @ApiResponse(responseCode = "404", description = "City not found"),
            @ApiResponse(responseCode = "204", description = "No districts found")
    })
    @GetMapping("/city/{cityCode}")
    public ResponseEntity<List<DistrictDTO>> getDistrictsByCity(@PathVariable String cityCode) {
        List<DistrictDTO> districts = locationService.findDistrictsByCityCode(cityCode);
        if (districts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(districts);
    }

    @Operation(summary = "Get district by code", description = "Retrieve a district by its code and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "District found"),
            @ApiResponse(responseCode = "404", description = "District not found")
    })
    @GetMapping("/{districtCode}/city/{cityCode}")
    public ResponseEntity<DistrictDTO> getDistrictByCode(@PathVariable String districtCode, @PathVariable String cityCode) {
        DistrictDTO districtDTO = locationService.findDistrictByCode(districtCode, cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with code: " + districtCode + " in city: " + cityCode));
        return ResponseEntity.ok(districtDTO);
    }

    @Operation(summary = "Create a new district", description = "Create a new district with a unique name and code within a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "District created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "District with this name or code already exists in the city")
    })
    @PostMapping
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody @Valid DistrictDTO districtDTO) {
        DistrictDTO createdDistrict = locationService.createDistrict(districtDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDistrict);
    }

    @Operation(summary = "Update a district by code", description = "Update a district's information by its code and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "District updated successfully"),
            @ApiResponse(responseCode = "404", description = "District not found"),
            @ApiResponse(responseCode = "409", description = "District with this name or code already exists in the city")
    })
    @PutMapping("/{districtCode}/city/{cityCode}")
    public ResponseEntity<DistrictDTO> updateDistrictByCode(@PathVariable String districtCode, @PathVariable String cityCode, @RequestBody @Valid DistrictDTO updatedDistrictDTO) {
        DistrictDTO updatedDistrict = locationService.updateDistrictByCode(districtCode, cityCode, updatedDistrictDTO)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with code: " + districtCode + " in city: " + cityCode));
        return ResponseEntity.ok(updatedDistrict);
    }

    @Operation(summary = "Delete a district by code", description = "Delete a district by its code and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "District deleted successfully"),
            @ApiResponse(responseCode = "404", description = "District not found")
    })
    @DeleteMapping("/{districtCode}/city/{cityCode}")
    public ResponseEntity<Void> deleteDistrictByCode(@PathVariable String districtCode, @PathVariable String cityCode) {
        locationService.deleteDistrictByCode(districtCode, cityCode);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all districts", description = "Retrieve a list of all districts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of districts"),
            @ApiResponse(responseCode = "204", description = "No districts found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = locationService.findAllDistricts();
        if (districts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(districts);
    }
}
