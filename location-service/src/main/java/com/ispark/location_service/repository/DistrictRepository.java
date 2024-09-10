package com.ispark.location_service.repository;

import com.ispark.location_service.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByDistrictCodeAndCity_CityCode(String districtCode, String cityCode);
    Optional<District> findByDistrictNameAndCity_CityCode(String districtName, String cityCode);
    List<District> findByCity_CityCode(String cityCode);
    void deleteByDistrictCodeAndCity_CityCode(String districtCode, String cityCode);
}
