package com.ispark.location_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "streets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"streetName", "district_id"}),
        @UniqueConstraint(columnNames = {"streetCode", "district_id"})
})
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long streetId;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false, unique = true)
    private String streetCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
