package com.ispark.tariff_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tariffs")
@Data
@NoArgsConstructor
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tarifeNo;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tariff_id")
    private List<TariffDetail> details;

    private String lastUpdatedBy;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
