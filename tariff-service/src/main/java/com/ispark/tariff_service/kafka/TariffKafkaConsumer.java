package com.ispark.tariff_service.kafka;

import com.ispark.tariff_service.model.Tariff;
import com.ispark.tariff_service.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TariffKafkaConsumer {

    @Autowired
    private TariffService tariffService;

    @KafkaListener(topics = "tariff-topic", groupId = "tariff-group")
    public void listen(Tariff kafkaTariff) {
        if (kafkaTariff.getName().contains("DUPLICATE")) {
            // Perform duplication logic
            Tariff newTariff = tariffService.duplicateAndChangeName(kafkaTariff);
            System.out.println("Duplicated tariff with new name: " + newTariff.getName());
        } else {
            // Create a new tariff normally
            Tariff newTariff = tariffService.createTariffFromKafka(kafkaTariff);
            System.out.println("Created new tariff with tarifeNo: " + newTariff.getTarifeNo());
        }
    }
}
