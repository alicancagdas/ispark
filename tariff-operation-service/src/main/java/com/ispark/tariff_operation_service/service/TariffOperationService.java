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

    /**
     * Modify the prices of tariff details based on the operation and send the updated tariff to Kafka.
     *
     * @param tarifeNo   The tariff number to identify the tariff.
     * @param operation  The operation to be performed on the price (add, subtract, multiply, percentage).
     * @param value      The value to apply with the operation.
     * @param tariffName Optional. If not provided, the tariff name will include the modification timestamp.
     * @return The modified Tariff object.
     */
    public Tariff modifyTariffPrice(String tarifeNo, String operation, double value, String tariffName) {
        Optional<Tariff> optionalTariff = tariffRepository.findByTarifeNo(tarifeNo);

        if (optionalTariff.isPresent()) {
            Tariff tariff = optionalTariff.get();

            // Modify each tariff detail's price based on the operation
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
                        throw new IllegalArgumentException("Invalid operation: " + operation);
                }
            });

            // If no new name is provided, append the modification timestamp to the tariff name
            if (tariffName == null || tariffName.isEmpty()) {
                tariff.setName("Modified on " + LocalDateTime.now() + " - " + tariff.getName());
            } else {
                tariff.setName(tariffName);
            }

            // Save the modified tariff in the database
            tariff = tariffRepository.save(tariff);

            // Send the modified tariff to Kafka
            tariffKafkaProducer.sendTariff(tariff);

            return tariff;
        } else {
            throw new IllegalArgumentException("Tariff not found with tarifeNo: " + tarifeNo);
        }
    }
}
