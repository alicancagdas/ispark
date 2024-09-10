package com.ispark.location_service.controller;

import com.ispark.location_service.dto.StreetDTO;
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
@RequestMapping("/api/streets")
@Tag(name = "Street", description = "Operations related to street management")
public class StreetController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Get streets by district code and city code", description = "Retrieve a list of streets by district code and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of streets"),
            @ApiResponse(responseCode = "404", description = "District not found"),
            @ApiResponse(responseCode = "204", description = "No streets found")
    })
    @GetMapping("/district/{districtCode}/city/{cityCode}")
    public ResponseEntity<List<StreetDTO>> getStreetsByDistrict(@PathVariable String districtCode, @PathVariable String cityCode) {
        List<StreetDTO> streets = locationService.findStreetsByDistrictCode(districtCode, cityCode);
        if (streets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(streets);
    }

    @Operation(summary = "Get street by code", description = "Retrieve a street by its code, district code, and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Street found"),
            @ApiResponse(responseCode = "404", description = "Street not found")
    })
    @GetMapping("/{streetCode}/district/{districtCode}/city/{cityCode}")
    public ResponseEntity<StreetDTO> getStreetByCode(@PathVariable String streetCode, @PathVariable String districtCode, @PathVariable String cityCode) {
        StreetDTO streetDTO = locationService.findStreetByCode(streetCode, districtCode, cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("Street not found with code: " + streetCode + " in district: " + districtCode + " and city: " + cityCode));
        return ResponseEntity.ok(streetDTO);
    }

    @Operation(summary = "Create a new street", description = "Create a new street with a unique name and code within a district and city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Street created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Street with this name or code already exists in the district")
    })
    @PostMapping
    public ResponseEntity<StreetDTO> createStreet(@RequestBody @Valid StreetDTO streetDTO) {
        StreetDTO createdStreet = locationService.createStreet(streetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStreet);
    }

    @Operation(summary = "Update a street by code", description = "Update a street's information by its code, district code, and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Street updated successfully"),
            @ApiResponse(responseCode = "404", description = "Street not found"),
            @ApiResponse(responseCode = "409", description = "Street with this name or code already exists in the district")
    })
    @PutMapping("/{streetCode}/district/{districtCode}/city/{cityCode}")
    public ResponseEntity<StreetDTO> updateStreetByCode(@PathVariable String streetCode, @PathVariable String districtCode, @PathVariable String cityCode, @RequestBody @Valid StreetDTO updatedStreetDTO) {
        StreetDTO updatedStreet = locationService.updateStreetByCode(streetCode, districtCode, cityCode, updatedStreetDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Street not found with code: " + streetCode + " in district: " + districtCode + " and city: " + cityCode));
        return ResponseEntity.ok(updatedStreet);
    }

    @Operation(summary = "Delete a street by code", description = "Delete a street by its code, district code, and city code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Street deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Street not found")
    })
    @DeleteMapping("/{streetCode}/district/{districtCode}/city/{cityCode}")
    public ResponseEntity<Void> deleteStreetByCode(@PathVariable String streetCode, @PathVariable String districtCode, @PathVariable String cityCode) {
        locationService.deleteStreetByCode(streetCode, districtCode, cityCode);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all streets", description = "Retrieve a list of all streets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of streets"),
            @ApiResponse(responseCode = "204", description = "No streets found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<StreetDTO>> getAllStreets() {
        List<StreetDTO> streets = locationService.findAllStreets();
        if (streets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(streets);
    }

}
