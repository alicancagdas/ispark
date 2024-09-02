package com.ispark.tariff_operation_service.repository;

import com.ispark.tariff_operation_service.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByTarifeNo(String tarifeNo);
}
