package com.ispark.location_service.dto;

import com.ispark.location_service.entity.District;
import com.ispark.location_service.entity.City;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {

    private Long districtId;
    private String districtCode;
    private String districtName;
    private String cityCode;
    private List<StreetDTO> streets;

    public static DistrictDTO fromEntity(District district) {
        return DistrictDTO.builder()
                .districtId(district.getDistrictId())
                .districtCode(district.getDistrictCode())
                .districtName(district.getDistrictName())
                .cityCode(district.getCity().getCityCode())
                .streets(district.getStreets() != null ? district.getStreets().stream()
                        .map(StreetDTO::fromEntity)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public District toEntity(City city) {
        return District.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .city(city)
                .build();
    }
}
