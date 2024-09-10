package com.ispark.location_service.repository;

import com.ispark.location_service.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityCode(String cityCode);
    Optional<City> findByCityName(String cityName);
    void deleteByCityCode(String cityCode);
}
