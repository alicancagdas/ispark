package com.ispark.tariff_operation_service.service;

import com.ispark.tariff_operation_service.kafka.TariffKafkaProducer;
import com.ispark.tariff_operation_service.model.Tariff;
import com.ispark.tariff_operation_service.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TariffOperationService {

    @Autowired
    private TariffKafkaProducer tariffKafkaProducer;

    @Autowired
    private TariffRepository tariffRepository;

    public Tariff modifyTariffPrice(String tarifeNo, String operation, double value, String tariffName) {
        Optional<Tariff> optionalTariff = tariffRepository.findByTarifeNo(tarifeNo);

        if (optionalTariff.isPresent()) {
            Tariff tariff = optionalTariff.get();

            tariff.getDetails().forEach(detail -> {
                switch (operation.toLowerCase()) {
                    case "add":
                        detail.setPrice(detail.getPrice() + value);
                        break;
                    case "subtract":
                        detail.setPrice(detail.getPrice() - value);
                        break;
                    case "multiply":
                        detail.setPrice(detail.getPrice() * value);
                        break;
                    case "percentage":
                        detail.setPrice(detail.getPrice() * (1 + value / 100));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operation");
                }
            });

            if (tariffName == null || tariffName.isEmpty()) {
                tariff.setName("Modified on " + LocalDateTime.now() + " - " + tariff.getName());
            } else {
                tariff.setName(tariffName);
            }

            tariffKafkaProducer.sendTariff(tariff);

            return tariff;
        } else {
            throw new IllegalArgumentException("Tariff not found with tarifeNo: " + tarifeNo);
        }
    }
}
