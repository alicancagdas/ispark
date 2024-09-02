package com.ispark.tariff_service.service;

import com.ispark.tariff_service.model.Tariff;

import java.time.LocalDateTime;

public class TariffFactory {

    public static Tariff createTariff(Tariff tariff, String lastUpdatedBy) {
        tariff.setTarifeNo(generateTarifeNo());
        tariff.setCreateDate(LocalDateTime.now());
        tariff.setUpdateDate(LocalDateTime.now());
        tariff.setLastUpdatedBy(lastUpdatedBy);
        return tariff;
    }

    private static String generateTarifeNo() {
        return "TARIFE-" + System.currentTimeMillis();
    }
}
