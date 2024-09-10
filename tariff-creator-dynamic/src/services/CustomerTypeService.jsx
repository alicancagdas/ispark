// src/services/customerTypeService.js

export const fetchCustomerTypes = async () => {
    try {
      const response = await fetch('http://localhost:8082/api/customer-types');
      return await response.json();
    } catch (error) {
      console.error('Error fetching customer types:', error);
      throw error;
    }
  };
  
  export const createCustomerType = async (customerTypeData) => {
    try {
      const response = await fetch('http://localhost:8082/api/customer-types', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerTypeData),
      });
      return await response.json();
    } catch (error) {
      console.error('Error creating customer type:', error);
      throw error;
    }
  };
  
  export const updateCustomerType = async (customerTypeNo, customerTypeData) => {
    try {
      const response = await fetch(`http://localhost:8082/api/customer-types/${customerTypeNo}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerTypeData),
      });
      return await response.json();
    } catch (error) {
      console.error('Error updating customer type:', error);
      throw error;
    }
  };
  
  export const deleteCustomerType = async (customerTypeNo) => {
    try {
      const response = await fetch(`http://localhost:8082/api/customer-types/${customerTypeNo}`, {
        method: 'DELETE',
      });
      return await response.json();
    } catch (error) {
      console.error('Error deleting customer type:', error);
      throw error;
    }
  };
  