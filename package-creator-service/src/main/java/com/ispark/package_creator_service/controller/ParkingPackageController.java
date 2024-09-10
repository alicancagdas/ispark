package com.ispark.package_creator_service.controller;


import com.ispark.packagecreator.dto.ParkingPackageDto;
import com.ispark.packagecreator.model.ParkingPackage;
import com.ispark.packagecreator.service.ParkingPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-packages")
@RequiredArgsConstructor
public class ParkingPackageController {

    private final ParkingPackageService parkingPackageService;

    @PostMapping
    public ResponseEntity<ParkingPackage> createParkingPackage(@RequestBody ParkingPackageDto parkingPackageDto) {
        ParkingPackage parkingPackage = parkingPackageService.createParkingPackage(parkingPackageDto);
        return new ResponseEntity<>(parkingPackage, HttpStatus.CREATED);
    }

    @PostMapping("/send-to-kafka")
    public ResponseEntity<String> sendPackageToKafka(@RequestBody ParkingPackageDto parkingPackageDto) {
        ParkingPackage parkingPackage = parkingPackageService.createParkingPackage(parkingPackageDto);
        parkingPackageService.sendPackageToKafka(parkingPackage);
        return new ResponseEntity<>("Package sent to Kafka successfully", HttpStatus.OK);
    }
}
