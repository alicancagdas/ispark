package com.ispark.tariff_operation_service.kafka;

import com.ispark.tariff_operation_service.model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TariffKafkaProducer {

    private static final String TOPIC = "tariff-topic";

    @Autowired
    private KafkaTemplate<String, Tariff> kafkaTemplate;

    public void sendTariff(Tariff tariff) {
        kafkaTemplate.send(TOPIC, tariff);
        System.out.println("Kafka Producer - Sent tariff: " + tariff.getTarifeNo());
    }

    public void sendDuplicatedTariff(Tariff duplicatedTariff) {
        kafkaTemplate.send("tariff-topic", duplicatedTariff);
        System.out.println("Duplicated tariff sent to Kafka: " + duplicatedTariff.getTarifeNo());
    }
}
