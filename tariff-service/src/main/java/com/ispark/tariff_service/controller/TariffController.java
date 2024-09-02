package com.ispark.tariff_service.controller;

import com.ispark.tariff_service.model.Tariff;
import com.ispark.tariff_service.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin(origins = "*") // Allows all origins
@RestController
@RequestMapping("/api/v1/tariffs")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @GetMapping
    public ResponseEntity<List<Tariff>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.getAllTariffs());
    }

    @GetMapping("/tarifeNo/{tarifeNo}")
    public ResponseEntity<Tariff> getTariffByTarifeNo(@PathVariable String tarifeNo) {
        Tariff tariff = tariffService.getTariffByTarifeNo(tarifeNo);
        return ResponseEntity.ok(tariff);
    }

    @PostMapping
    public ResponseEntity<Tariff> createTariff(@RequestBody Tariff tariff) {
        Tariff createdTariff = tariffService.createTariff(tariff);
        return new ResponseEntity<>(createdTariff, HttpStatus.CREATED);
    }


    @PutMapping("/tarifeNo/{tarifeNo}")
    public ResponseEntity<Tariff> updateTariff(@PathVariable String tarifeNo, @RequestBody Tariff updatedTariff) {
        Tariff updated = tariffService.updateTariffByTarifeNo(tarifeNo, updatedTariff);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/tarifeNo/{tarifeNo}")
    public ResponseEntity<Void> deleteTariff(@PathVariable String tarifeNo) {
        tariffService.deleteTariffByTarifeNo(tarifeNo);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTariffs() {
        try {
            tariffService.deleteAllTariffs();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Tariff>> getTariffByName(@PathVariable String name) {
        List<Tariff> tariffs = tariffService.getTariffByName(name);
        return ResponseEntity.ok(tariffs);
    }
}
