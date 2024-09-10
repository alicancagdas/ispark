package com.ispark.location_service.dto;

import com.ispark.location_service.entity.Street;
import com.ispark.location_service.entity.District;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetDTO {

    private Long streetId;
    private String streetName;
    private String streetCode;
    private String districtCode;
    private String cityCode;

    public static StreetDTO fromEntity(Street street) {
        return StreetDTO.builder()
                .streetId(street.getStreetId())
                .streetName(street.getStreetName())
                .streetCode(street.getStreetCode())
                .districtCode(street.getDistrict().getDistrictCode())
                .cityCode(street.getDistrict().getCity().getCityCode())
                .build();
    }

    public Street toEntity(District district) {
        return Street.builder()
                .streetName(streetName)
                .streetCode(streetCode)
                .district(district)
                .build();
    }
}
