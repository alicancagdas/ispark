import React, { useState, useEffect } from 'react';
import { Button, Form, Table, Card, Modal } from 'react-bootstrap';

function VehicleType() {
  const [vehicleTypes, setVehicleTypes] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingVehicleType, setEditingVehicleType] = useState(null);
  const [newVehicleType, setNewVehicleType] = useState({ typeNo: '', name: '' });

  useEffect(() => {
    fetchVehicleTypes();
  }, []);

  const fetchVehicleTypes = () => {
    fetch('http://localhost:8083/api/vehicle-types')
      .then(response => response.json())
      .then(data => setVehicleTypes(data))
      .catch(error => console.error('Error fetching vehicle types:', error));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (editingVehicleType) {
      fetch(`http://localhost:8083/api/vehicle-types/${editingVehicleType.typeNo}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newVehicleType),
      })
        .then(() => {
          fetchVehicleTypes();
          setShowModal(false);
          setEditingVehicleType(null);
        })
        .catch((error) => console.error('Error updating vehicle type:', error));
    } else {
      fetch('http://localhost:8083/api/vehicle-types', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newVehicleType),
      })
        .then(() => {
          fetchVehicleTypes();
          setShowModal(false);
        })
        .catch((error) => console.error('Error creating vehicle type:', error));
    }

    setNewVehicleType({ typeNo: '', name: '' });
  };

  return (
    <Card>
      <Card.Header>
        <div className="d-flex justify-content-between align-items-center">
          <h4>Vehicle Types</h4>
          <Button variant="primary" onClick={() => setShowModal(true)}>+ Add New Vehicle Type</Button>
        </div>
      </Card.Header>
      <Card.Body>
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>#</th>
              <th>Type No</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {vehicleTypes.map((vehicleType, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{vehicleType.typeNo}</td>
                <td>{vehicleType.name}</td>
                <td>
                  <Button variant="warning" className="me-2" onClick={() => setEditingVehicleType(vehicleType)}>
                    Edit
                  </Button>
                  <Button variant="danger" className="me-2" onClick={() => handleDelete(vehicleType.typeNo)}>
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Card.Body>

      {/* Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>{editingVehicleType ? 'Edit Vehicle Type' : 'Add New Vehicle Type'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formVehicleTypeNo">
              <Form.Label>Type No</Form.Label>
              <Form.Control
                type="text"
                value={newVehicleType.typeNo}
                onChange={(e) => setNewVehicleType({ ...newVehicleType, typeNo: e.target.value })}
                required
                disabled={!!editingVehicleType}
              />
            </Form.Group>
            <Form.Group controlId="formVehicleTypeName" className="mt-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                value={newVehicleType.name}
                onChange={(e) => setNewVehicleType({ ...newVehicleType, name: e.target.value })}
                required
              />
            </Form.Group>
            <Button variant="primary" type="submit" className="mt-3">
              {editingVehicleType ? 'Update Vehicle Type' : 'Save Vehicle Type'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </Card>
  );
}

export default VehicleType;
