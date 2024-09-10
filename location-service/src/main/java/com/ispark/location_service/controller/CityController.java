package com.ispark.location_service.controller;

import com.ispark.location_service.dto.CityDTO;
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
@RequestMapping("/api/cities")
@Tag(name = "City", description = "Operations related to city management")
public class CityController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Get all cities", description = "Retrieve a list of all cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cities"),
            @ApiResponse(responseCode = "204", description = "No cities found")
    })
    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<CityDTO> cities = locationService.findAllCities();
        if (cities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cities);
    }

    @Operation(summary = "Get city by code", description = "Retrieve a city by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City found"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{cityCode}")
    public ResponseEntity<CityDTO> getCityByCode(@PathVariable String cityCode) {
        CityDTO cityDTO = locationService.findCityByCode(cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with code: " + cityCode));
        return ResponseEntity.ok(cityDTO);
    }

    @Operation(summary = "Create a new city", description = "Create a new city with a unique name and code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "City created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "City with this name or code already exists")
    })
    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody @Valid CityDTO cityDTO) {
        CityDTO createdCity = locationService.createCity(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @Operation(summary = "Update a city by code", description = "Update a city's information by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City updated successfully"),
            @ApiResponse(responseCode = "404", description = "City not found"),
            @ApiResponse(responseCode = "409", description = "City with this name or code already exists")
    })
    @PutMapping("/{cityCode}")
    public ResponseEntity<CityDTO> updateCityByCode(@PathVariable String cityCode, @RequestBody @Valid CityDTO updatedCityDTO) {
        CityDTO updatedCity = locationService.updateCityByCode(cityCode, updatedCityDTO)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with code: " + cityCode));
        return ResponseEntity.ok(updatedCity);
    }

    @Operation(summary = "Delete a city by code", description = "Delete a city by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "City deleted successfully"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @DeleteMapping("/{cityCode}")
    public ResponseEntity<Void> deleteCityByCode(@PathVariable String cityCode) {
        locationService.deleteCityByCode(cityCode);
        return ResponseEntity.noContent().build();
    }
}
