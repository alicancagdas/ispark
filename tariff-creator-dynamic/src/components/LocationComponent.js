import React, { useState } from 'react';
import { Tabs, Tab } from 'react-bootstrap';
import CityComponent from './CityComponent';
import DistrictComponent from './DistrictComponent';
import StreetComponent from './StreetComponent';

function LocationComponent() {
  const [selectedCity, setSelectedCity] = useState(null);
  const [selectedDistrict, setSelectedDistrict] = useState(null);
  const [districtUpdated, setDistrictUpdated] = useState(false); // Trigger for re-fetching districts

  const handleCitySelect = (city) => {
    setSelectedCity(city); // When a city is selected, update city state
    setSelectedDistrict(null); // Reset district selection when city changes
  };

  const handleDistrictSelect = (district) => {
    setSelectedDistrict(district); // When a district is selected, update district state
  };

  return (
    <div>
      <Tabs defaultActiveKey="city" className="mb-3">
        <Tab eventKey="city" title="City">
          <CityComponent onCitySelect={handleCitySelect} />
        </Tab>
        <Tab eventKey="district" title="District" disabled={!selectedCity}>
          <DistrictComponent
            selectedCity={selectedCity}
            onDistrictSelect={handleDistrictSelect}
            districtUpdated={districtUpdated}
            setDistrictUpdated={setDistrictUpdated}
          />
        </Tab>
        <Tab eventKey="street" title="Street" disabled={!selectedDistrict}>
          <StreetComponent selectedDistrict={selectedDistrict} selectedCity={selectedCity} />
        </Tab>
      </Tabs>
    </div>
  );
}

export default LocationComponent;
