package com.ispark.tariff_operation_service.controller;

import com.ispark.tariff_operation_service.dto.ModifyPriceRequest;
import com.ispark.tariff_operation_service.model.Tariff;
import com.ispark.tariff_operation_service.service.TariffOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*") // Allows all origins
@RestController
@RequestMapping("/api/v1/tariff-operations")
public class TariffOperationController {

    @Autowired
    private TariffOperationService tariffOperationService;

    @PutMapping("/tarifeNo/{tarifeNo}/modify-price")
    public ResponseEntity<Tariff> modifyTariffPrice(
            @PathVariable String tarifeNo,
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "value", required = false) Double value,
            @RequestParam(value = "tariffName", required = false) String tariffName,
            @RequestBody(required = false) ModifyPriceRequest modifyPriceRequest) {

        if (modifyPriceRequest != null) {
            operation = modifyPriceRequest.getOperation();
            value = modifyPriceRequest.getValue();
            tariffName = modifyPriceRequest.getTariffName();
        }

        if (operation == null || value == null) {
            throw new IllegalArgumentException("Operation and value must be provided either as query parameters or in the JSON body.");
        }

        Tariff updatedTariff = tariffOperationService.modifyTariffPrice(tarifeNo, operation, value, tariffName);
        return ResponseEntity.ok(updatedTariff);
    }
}
