package com.ispark.package_creator_service.service;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "package-topic", groupId = "parking_group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
