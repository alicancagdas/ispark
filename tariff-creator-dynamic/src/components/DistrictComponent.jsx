import React, { useState, useEffect } from 'react';
import { Button, Form, Modal, Accordion } from 'react-bootstrap';
import StreetComponent from './StreetComponent';

function DistrictComponent({ selectedCity, onDistrictSelect, districtUpdated, setDistrictUpdated }) {
  const [districts, setDistricts] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newDistrict, setNewDistrict] = useState({ districtCode: '', districtName: '', cityCode: '' });
  const [editingDistrict, setEditingDistrict] = useState(null);

  useEffect(() => {
    if (selectedCity && selectedCity.cityCode) {
      fetchDistricts(selectedCity.cityCode);
    }
  }, [selectedCity, districtUpdated]);

  const fetchDistricts = (cityCode) => {
    fetch(`http://localhost:8084/api/districts/city/${cityCode}`)
      .then(response => response.json())
      .then(data => setDistricts(data))
      .catch(error => console.error('Error fetching districts:', error));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const url = editingDistrict
      ? `http://localhost:8084/api/districts/${editingDistrict.districtCode}/city/${selectedCity.cityCode}`
      : 'http://localhost:8084/api/districts';
    const method = editingDistrict ? 'PUT' : 'POST';

    const districtToSubmit = { ...newDistrict, cityCode: selectedCity.cityCode };

    fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(districtToSubmit),
    })
      .then(() => {
        setDistrictUpdated(!districtUpdated); // District güncellemesini tetikleyelim
        setShowModal(false);
        setNewDistrict({ districtCode: '', districtName: '', cityCode: '' });
        setEditingDistrict(null);
      })
      .catch((error) => console.error(`Error ${editingDistrict ? 'updating' : 'creating'} district:`, error));
  };

  const handleEdit = (district) => {
    setEditingDistrict(district);
    setNewDistrict({ districtCode: district.districtCode, districtName: district.districtName });
    setShowModal(true);
  };

  const handleDelete = (districtCode) => {
    fetch(`http://localhost:8084/api/districts/${districtCode}/city/${selectedCity.cityCode}`, {
      method: 'DELETE',
    })
      .then(() => setDistrictUpdated(!districtUpdated)) // Güncelleme tetikleniyor
      .catch((error) => console.error('Error deleting district:', error));
  };

  if (!selectedCity) {
    return <div>Please select a city first</div>;
  }

  return (
    <div>
      <h4>Districts in {selectedCity.cityName}</h4>
      <Accordion>
        {districts.map((district, index) => (
          <Accordion.Item key={index} eventKey={index}>
            <Accordion.Header onClick={() => onDistrictSelect(district)}>
              {district.districtName} ({district.districtCode})
            </Accordion.Header>
            <Accordion.Body>
              <div className="d-flex justify-content-between mb-3">
                <Button variant="warning" onClick={() => handleEdit(district)}>Edit</Button>
                <Button variant="danger" onClick={() => handleDelete(district.districtCode)}>Delete</Button>
              </div>
              <StreetComponent selectedDistrict={district} selectedCity={selectedCity} />
            </Accordion.Body>
          </Accordion.Item>
        ))}
      </Accordion>

      <Button variant="primary" className="mt-3" onClick={() => setShowModal(true)}>+ Add New District</Button>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>{editingDistrict ? 'Edit District' : 'Add New District'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="districtCode">
              <Form.Label>District Code</Form.Label>
              <Form.Control
                type="text"
                value={newDistrict.districtCode}
                onChange={(e) => setNewDistrict({ ...newDistrict, districtCode: e.target.value })}
                disabled={!!editingDistrict}
                required
              />
            </Form.Group>
            <Form.Group controlId="districtName" className="mt-3">
              <Form.Label>District Name</Form.Label>
              <Form.Control
                type="text"
                value={newDistrict.districtName}
                onChange={(e) => setNewDistrict({ ...newDistrict, districtName: e.target.value })}
                required
              />
            </Form.Group>
            <Button variant="primary" type="submit" className="mt-3">
              {editingDistrict ? 'Update District' : 'Save District'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </div>
  );
}

export default DistrictComponent;
