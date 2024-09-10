package com.ispark.location_service.service;

import com.ispark.location_service.dto.CityDTO;
import com.ispark.location_service.dto.DistrictDTO;
import com.ispark.location_service.dto.StreetDTO;
import com.ispark.location_service.entity.City;
import com.ispark.location_service.entity.District;
import com.ispark.location_service.entity.Street;
import com.ispark.location_service.repository.CityRepository;
import com.ispark.location_service.repository.DistrictRepository;
import com.ispark.location_service.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private StreetRepository streetRepository;

    // City Operations

    @Transactional
    public CityDTO createCity(CityDTO cityDTO) {
        if (cityRepository.findByCityName(cityDTO.getCityName()).isPresent()) {
            throw new IllegalArgumentException("City with this name already exists");
        }
        if (cityRepository.findByCityCode(cityDTO.getCityCode()).isPresent()) {
            throw new IllegalArgumentException("City with this code already exists");
        }
        City city = cityDTO.toEntity();
        City savedCity = cityRepository.save(city);
        return CityDTO.fromEntity(savedCity);
    }

    public Optional<CityDTO> findCityByCode(String cityCode) {
        return cityRepository.findByCityCode(cityCode)
                .map(CityDTO::fromEntity);
    }

    public List<CityDTO> findAllCities() {
        return cityRepository.findAll().stream()
                .map(CityDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<CityDTO> updateCityByCode(String cityCode, CityDTO updatedCityDTO) {
        return cityRepository.findByCityCode(cityCode).map(city -> {
            if (!city.getCityName().equals(updatedCityDTO.getCityName()) &&
                    cityRepository.findByCityName(updatedCityDTO.getCityName()).isPresent()) {
                throw new IllegalArgumentException("City with this name already exists");
            }
            if (!city.getCityCode().equals(updatedCityDTO.getCityCode()) &&
                    cityRepository.findByCityCode(updatedCityDTO.getCityCode()).isPresent()) {
                throw new IllegalArgumentException("City with this code already exists");
            }
            city.setCityName(updatedCityDTO.getCityName());
            city.setCityCode(updatedCityDTO.getCityCode());
            city.setUpdatedAt(java.time.LocalDateTime.now());
            City updatedCity = cityRepository.save(city);
            return CityDTO.fromEntity(updatedCity);
        });
    }

    @Transactional
    public void deleteCityByCode(String cityCode) {
        if (!cityRepository.findByCityCode(cityCode).isPresent()) {
            throw new IllegalArgumentException("City not found");
        }
        cityRepository.deleteByCityCode(cityCode);
    }

    // District Operations

    @Transactional
    public DistrictDTO createDistrict(DistrictDTO districtDTO) {
        City city = cityRepository.findByCityCode(districtDTO.getCityCode())
                .orElseThrow(() -> new IllegalArgumentException("City not found"));

        if (districtRepository.findByDistrictNameAndCity_CityCode(districtDTO.getDistrictName(), districtDTO.getCityCode()).isPresent()) {
            throw new IllegalArgumentException("District with this name already exists in the city");
        }
        if (districtRepository.findByDistrictCodeAndCity_CityCode(districtDTO.getDistrictCode(), districtDTO.getCityCode()).isPresent()) {
            throw new IllegalArgumentException("District with this code already exists in the city");
        }

        District district = districtDTO.toEntity(city);
        District savedDistrict = districtRepository.save(district);
        return DistrictDTO.fromEntity(savedDistrict);
    }

    public Optional<DistrictDTO> findDistrictByCode(String districtCode, String cityCode) {
        return districtRepository.findByDistrictCodeAndCity_CityCode(districtCode, cityCode)
                .map(DistrictDTO::fromEntity);
    }

    public List<DistrictDTO> findDistrictsByCityCode(String cityCode) {
        return districtRepository.findByCity_CityCode(cityCode).stream()
                .map(DistrictDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<DistrictDTO> updateDistrictByCode(String districtCode, String cityCode, DistrictDTO updatedDistrictDTO) {
        return districtRepository.findByDistrictCodeAndCity_CityCode(districtCode, cityCode).map(district -> {
            if (!district.getDistrictName().equals(updatedDistrictDTO.getDistrictName()) &&
                    districtRepository.findByDistrictNameAndCity_CityCode(updatedDistrictDTO.getDistrictName(), cityCode).isPresent()) {
                throw new IllegalArgumentException("District with this name already exists in the city");
            }
            if (!district.getDistrictCode().equals(updatedDistrictDTO.getDistrictCode()) &&
                    districtRepository.findByDistrictCodeAndCity_CityCode(updatedDistrictDTO.getDistrictCode(), cityCode).isPresent()) {
                throw new IllegalArgumentException("District with this code already exists in the city");
            }
            district.setDistrictName(updatedDistrictDTO.getDistrictName());
            district.setDistrictCode(updatedDistrictDTO.getDistrictCode());
            district.setUpdatedAt(java.time.LocalDateTime.now());
            District updatedDistrict = districtRepository.save(district);
            return DistrictDTO.fromEntity(updatedDistrict);
        });
    }

    @Transactional
    public void deleteDistrictByCode(String districtCode, String cityCode) {
        if (!districtRepository.findByDistrictCodeAndCity_CityCode(districtCode, cityCode).isPresent()) {
            throw new IllegalArgumentException("District not found");
        }
        districtRepository.deleteByDistrictCodeAndCity_CityCode(districtCode, cityCode);
    }

    // Street Operations

    @Transactional
    public StreetDTO createStreet(StreetDTO streetDTO) {
        District district = districtRepository.findByDistrictCodeAndCity_CityCode(streetDTO.getDistrictCode(), streetDTO.getCityCode())
                .orElseThrow(() -> new IllegalArgumentException("District not found"));

        if (streetRepository.findByStreetNameAndDistrict_DistrictCodeAndDistrict_City_CityCode(streetDTO.getStreetName(), streetDTO.getDistrictCode(), streetDTO.getCityCode()).isPresent()) {
            throw new IllegalArgumentException("Street with this name already exists in the district");
        }
        if (streetRepository.findByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(streetDTO.getStreetCode(), streetDTO.getDistrictCode(), streetDTO.getCityCode()).isPresent()) {
            throw new IllegalArgumentException("Street with this code already exists in the district");
        }

        Street street = streetDTO.toEntity(district);
        Street savedStreet = streetRepository.save(street);
        return StreetDTO.fromEntity(savedStreet);
    }

    public Optional<StreetDTO> findStreetByCode(String streetCode, String districtCode, String cityCode) {
        return streetRepository.findByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(streetCode, districtCode, cityCode)
                .map(StreetDTO::fromEntity);
    }

    public List<StreetDTO> findStreetsByDistrictCode(String districtCode, String cityCode) {
        return streetRepository.findByDistrict_DistrictCodeAndDistrict_City_CityCode(districtCode, cityCode).stream()
                .map(StreetDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<StreetDTO> updateStreetByCode(String streetCode, String districtCode, String cityCode, StreetDTO updatedStreetDTO) {
        return streetRepository.findByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(streetCode, districtCode, cityCode).map(street -> {
            if (!street.getStreetName().equals(updatedStreetDTO.getStreetName()) &&
                    streetRepository.findByStreetNameAndDistrict_DistrictCodeAndDistrict_City_CityCode(updatedStreetDTO.getStreetName(), districtCode, cityCode).isPresent()) {
                throw new IllegalArgumentException("Street with this name already exists in the district");
            }
            if (!street.getStreetCode().equals(updatedStreetDTO.getStreetCode()) &&
                    streetRepository.findByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(updatedStreetDTO.getStreetCode(), districtCode, cityCode).isPresent()) {
                throw new IllegalArgumentException("Street with this code already exists in the district");
            }
            street.setStreetName(updatedStreetDTO.getStreetName());
            street.setStreetCode(updatedStreetDTO.getStreetCode());
            street.setUpdatedAt(java.time.LocalDateTime.now());
            Street updatedStreet = streetRepository.save(street);
            return StreetDTO.fromEntity(updatedStreet);
        });
    }

    @Transactional
    public void deleteStreetByCode(String streetCode, String districtCode, String cityCode) {
        if (!streetRepository.findByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(streetCode, districtCode, cityCode).isPresent()) {
            throw new IllegalArgumentException("Street not found");
        }
        streetRepository.deleteByStreetCodeAndDistrict_DistrictCodeAndDistrict_City_CityCode(streetCode, districtCode, cityCode);
    }

    // Retrieve all districts
    @Transactional(readOnly = true)
    public List<DistrictDTO> findAllDistricts() {
        return districtRepository.findAll().stream()
                .map(DistrictDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Retrieve all streets
    @Transactional(readOnly = true)
    public List<StreetDTO> findAllStreets() {
        return streetRepository.findAll().stream()
                .map(StreetDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
