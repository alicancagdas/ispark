package com.ispark.tariff_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logs")  // MongoDB'de "logs" koleksiyonunda saklanacak
public class LogEntry {

    @Id
    private String id;  // MongoDB'nin otomatik olarak ürettiği id

    private String level;  // INFO, ERROR, DEBUG gibi log seviyeleri

    private String message;  // Log mesajı

    private Date timestamp;  // Logun oluşturulduğu zaman
}
