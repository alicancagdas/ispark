package com.ispark.location_service.repository;

import com.ispark.location_service.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StreetRepository extends JpaRepository<Street, Long> {
    List<Street> findByDistrict_DistrictCodeAndDistrict_City_CityCode(String districtCode, String cityCode);
    Optional<Street> findByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(String streetCode, String districtCode, String cityCode);
    Optional<Street> findByStreetNameAndDistrict_DistrictCodeAndDistrict_City_CityCode(String streetName, String districtCode, String cityCode);
    void deleteByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(String streetCode, String districtCode, String cityCode);
}
