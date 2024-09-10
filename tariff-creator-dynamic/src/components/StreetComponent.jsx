import React, { useState, useEffect } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';

function StreetComponent({ selectedDistrict, selectedCity }) {
  const [streets, setStreets] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newStreet, setNewStreet] = useState({ streetCode: '', streetName: '', districtCode: '', cityCode: '' });
  const [editingStreet, setEditingStreet] = useState(null);

  useEffect(() => {
    if (selectedDistrict && selectedDistrict.districtCode && selectedCity && selectedCity.cityCode) {
      fetchStreets(selectedDistrict.districtCode, selectedCity.cityCode);
    }
  }, [selectedDistrict, selectedCity]);

  const fetchStreets = (districtCode, cityCode) => {
    fetch(`http://localhost:8084/api/streets/district/${districtCode}/city/${cityCode}`)
      .then(response => response.json())
      .then(data => setStreets(data))
      .catch(error => console.error('Error fetching streets:', error));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const url = editingStreet
      ? `http://localhost:8084/api/streets/${editingStreet.streetCode}`
      : 'http://localhost:8084/api/streets';
    const method = editingStreet ? 'PUT' : 'POST';

    const streetToSubmit = {
      ...newStreet,
      districtCode: selectedDistrict.districtCode,
      cityCode: selectedCity.cityCode,
    };

    fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(streetToSubmit),
    })
      .then(() => {
        fetchStreets(selectedDistrict.districtCode, selectedCity.cityCode);
        setShowModal(false);
        setNewStreet({ streetCode: '', streetName: '', districtCode: '', cityCode: '' });
        setEditingStreet(null);
      })
      .catch((error) => console.error(`Error ${editingStreet ? 'updating' : 'creating'} street:`, error));
  };

  const handleEdit = (street) => {
    setEditingStreet(street);
    setNewStreet({ streetCode: street.streetCode, streetName: street.streetName });
    setShowModal(true);
  };

  const handleDelete = (streetCode) => {
    fetch(`http://localhost:8084/api/streets/${streetCode}/district/${selectedDistrict.districtCode}/city/${selectedCity.cityCode}`, {
      method: 'DELETE',
    })
      .then(() => fetchStreets(selectedDistrict.districtCode, selectedCity.cityCode))
      .catch((error) => console.error('Error deleting street:', error));
  };

  if (!selectedDistrict || !selectedCity) {
    return <div>Please select both a city and a district first.</div>;
  }

  return (
    <div>
      <h4>Streets in {selectedDistrict.districtName}, {selectedCity.cityName}</h4>
      <ul>
        {streets.map((street, index) => (
          <li key={index}>
            {street.streetName} ({street.streetCode})
            <Button variant="warning" onClick={() => handleEdit(street)} className="mx-2">Edit</Button>
            <Button variant="danger" onClick={() => handleDelete(street.streetCode)}>Delete</Button>
          </li>
        ))}
      </ul>

      <Button variant="primary" className="mt-3" onClick={() => setShowModal(true)}>+ Add New Street</Button>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>{editingStreet ? 'Edit Street' : 'Add New Street'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="streetCode">
              <Form.Label>Street Code</Form.Label>
              <Form.Control
                type="text"
                value={newStreet.streetCode}
                onChange={(e) => setNewStreet({ ...newStreet, streetCode: e.target.value })}
                disabled={!!editingStreet}
                required
              />
            </Form.Group>
            <Form.Group controlId="streetName" className="mt-3">
              <Form.Label>Street Name</Form.Label>
              <Form.Control
                type="text"
                value={newStreet.streetName}
                onChange={(e) => setNewStreet({ ...newStreet, streetName: e.target.value })}
                required
              />
            </Form.Group>
            <Button variant="primary" type="submit" className="mt-3">
              {editingStreet ? 'Update Street' : 'Save Street'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </div>
  );
}

export default StreetComponent;
