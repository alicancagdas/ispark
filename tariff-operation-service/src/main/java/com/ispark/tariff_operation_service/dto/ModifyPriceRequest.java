package com.ispark.tariff_operation_service.dto;

import lombok.Data;

@Data
public class ModifyPriceRequest {
    private String operation;
    private double value;
    private String tariffName;
}
