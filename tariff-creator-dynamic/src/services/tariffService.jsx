// src/services/tariffService.js

export const fetchTariffs = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/v1/tariffs');
      return await response.json();
    } catch (error) {
      console.error('Error fetching tariffs:', error);
      throw error;
    }
  };
  
  export const createTariff = async (tariffData) => {
    try {
      const response = await fetch('http://localhost:8080/api/v1/tariffs', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(tariffData),
      });
      return await response.json();
    } catch (error) {
      console.error('Error creating tariff:', error);
      throw error;
    }
  };
  
  export const updateTariff = async (tarifeNo, tariffData) => {
    try {
      const response = await fetch(`http://localhost:8080/api/v1/tariffs/tarifeNo/${tarifeNo}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(tariffData),
      });
      return await response.json();
    } catch (error) {
      console.error('Error updating tariff:', error);
      throw error;
    }
  };
  
  export const deleteTariff = async (tarifeNo) => {
    try {
      const response = await fetch(`http://localhost:8080/api/v1/tariffs/tarifeNo/${tarifeNo}`, {
        method: 'DELETE',
      });
      return await response.json();
    } catch (error) {
      console.error('Error deleting tariff:', error);
      throw error;
    }
  };
  
  // Modify Tariff Price
  export const modifyTariffPrice = async (tarifeNo, operation, value) => {
    try {
      const response = await fetch(`http://localhost:8081/api/v1/tariff-operations/tarifeNo/${tarifeNo}/modify-price`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          operation,
          value: parseFloat(value),
        }),
      });
      return await response.json();
    } catch (error) {
      console.error('Error modifying tariff price:', error);
      throw error;
    }
  };
  