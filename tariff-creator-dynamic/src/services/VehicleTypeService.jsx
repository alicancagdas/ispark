// src/services/vehicleTypeService.js

export const fetchVehicleTypes = async () => {
    try {
      const response = await fetch('http://localhost:8083/api/vehicle-types');
      return await response.json();
    } catch (error) {
      console.error('Error fetching vehicle types:', error);
      throw error;
    }
  };
  
  export const createVehicleType = async (vehicleTypeData) => {
    try {
      const response = await fetch('http://localhost:8083/api/vehicle-types', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(vehicleTypeData),
      });
      return await response.json();
    } catch (error) {
      console.error('Error creating vehicle type:', error);
      throw error;
    }
  };
  
  export const updateVehicleType = async (typeNo, vehicleTypeData) => {
    try {
      const response = await fetch(`http://localhost:8083/api/vehicle-types/${typeNo}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(vehicleTypeData),
      });
      return await response.json();
    } catch (error) {
      console.error('Error updating vehicle type:', error);
      throw error;
    }
  };
  
  export const deleteVehicleType = async (typeNo) => {
    try {
      const response = await fetch(`http://localhost:8083/api/vehicle-types/${typeNo}`, {
        method: 'DELETE',
      });
      return await response.json();
    } catch (error) {
      console.error('Error deleting vehicle type:', error);
      throw error;
    }
  };
  