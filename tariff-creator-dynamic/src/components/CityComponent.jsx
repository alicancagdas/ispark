import React, { useState, useEffect } from 'react';
import { Button, Form, Card, Modal, Accordion } from 'react-bootstrap';

function CityComponent({ onCitySelect }) {
  const [cities, setCities] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingCity, setEditingCity] = useState(null);
  const [newCity, setNewCity] = useState({ cityCode: '', cityName: '' });
  const [cityUpdated, setCityUpdated] = useState(false); // Eklenen state

  useEffect(() => {
    fetchCities();
  }, [cityUpdated]); // cityUpdated değiştiğinde fetch işlemi tetiklenecek

  const fetchCities = () => {
    fetch('http://localhost:8084/api/cities')
      .then((response) => response.json())
      .then((data) => setCities(data))
      .catch((error) => console.error('Error fetching cities:', error));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const method = editingCity ? 'PUT' : 'POST';
    const url = editingCity
      ? `http://localhost:8084/api/cities/${editingCity.cityCode}`
      : 'http://localhost:8084/api/cities';

    fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newCity),
    })
      .then(() => {
        setCityUpdated(!cityUpdated); // City güncellemesini tetikle
        setShowModal(false);
        setNewCity({ cityCode: '', cityName: '' });
        setEditingCity(null);
      })
      .catch((error) => console.error('Error submitting city:', error));
  };

  const handleEdit = (city) => {
    setEditingCity(city);
    setNewCity({ cityCode: city.cityCode, cityName: city.cityName });
    setShowModal(true);
  };

  const handleDelete = (cityCode) => {
    fetch(`http://localhost:8084/api/cities/${cityCode}`, {
      method: 'DELETE',
    })
      .then(() => setCityUpdated(!cityUpdated)) // City silindiğinde güncellemeyi tetikle
      .catch((error) => console.error('Error deleting city:', error));
  };

  return (
    <Card>
      <Card.Header>
        <div className="d-flex justify-content-between align-items-center">
          <h4>City List</h4>
          <Button variant="primary" onClick={() => setShowModal(true)}>+ Add New City</Button>
        </div>
      </Card.Header>
      <Card.Body>
        <Accordion>
          {cities.map((city, index) => (
            <Accordion.Item key={index} eventKey={index}>
              <Accordion.Header onClick={() => onCitySelect(city)}>
                {city.cityName} ({city.cityCode})
              </Accordion.Header>
              <Accordion.Body>
                <div className="d-flex justify-content-between">
                  <Button variant="warning" onClick={() => handleEdit(city)}>Edit</Button>
                  <Button variant="danger" onClick={() => handleDelete(city.cityCode)}>Delete</Button>
                </div>
              </Accordion.Body>
            </Accordion.Item>
          ))}
        </Accordion>
      </Card.Body>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>{editingCity ? 'Edit City' : 'Add New City'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="cityCode">
              <Form.Label>City Code</Form.Label>
              <Form.Control
                type="text"
                value={newCity.cityCode}
                onChange={(e) => setNewCity({ ...newCity, cityCode: e.target.value })}
                disabled={!!editingCity}
                required
              />
            </Form.Group>
            <Form.Group controlId="cityName" className="mt-3">
              <Form.Label>City Name</Form.Label>
              <Form.Control
                type="text"
                value={newCity.cityName}
                onChange={(e) => setNewCity({ ...newCity, cityName: e.target.value })}
                required
              />
            </Form.Group>
            <Button variant="primary" type="submit" className="mt-3">
              {editingCity ? 'Update City' : 'Save City'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </Card>
  );
}

export default CityComponent;
