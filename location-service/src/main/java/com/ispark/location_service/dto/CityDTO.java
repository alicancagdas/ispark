package com.ispark.location_service.dto;

import com.ispark.location_service.entity.City;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    private Long cityId;
    private String cityName;
    private String cityCode;
    private List<DistrictDTO> districts;

    public static CityDTO fromEntity(City city) {
        return CityDTO.builder()
                .cityId(city.getCityId())
                .cityName(city.getCityName())
                .cityCode(city.getCityCode())
                .districts(city.getDistricts() != null ? city.getDistricts().stream()
                        .map(DistrictDTO::fromEntity)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public City toEntity() {
        return City.builder()
                .cityName(cityName)
                .cityCode(cityCode)
                .build();
    }
}
