package com.ispark.tariff_service.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "tariff_details")
@Data
public class TariffDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private Double price;

    // Varsayılan yapıcı metot
    public TariffDetail() {}

    // Yapıcı metot
    public TariffDetail(String startTime, String endTime, Double price) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }
}
