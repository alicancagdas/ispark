package com.ispark.tariff_service.kafka;

import com.ispark.tariff_service.model.Tariff;
import com.ispark.tariff_service.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TariffKafkaConsumer {

    @Autowired
    private TariffService tariffService;

    @KafkaListener(topics = "tariff-topic", groupId = "tariff-group")
    public void listen(Tariff kafkaTariff) {
        // Kafka'dan gelen tarifeyi işleyin ve yeni bir tarife oluşturun
        Tariff newTariff = tariffService.createTariffFromKafka(kafkaTariff);
        System.out.println("Created new tariff with tarifeNo: " + newTariff.getTarifeNo());
    }
}
