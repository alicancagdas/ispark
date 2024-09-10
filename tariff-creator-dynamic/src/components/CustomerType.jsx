import React, { useState, useEffect } from 'react';
import { Button, Form, Table, Card, Modal, Accordion } from 'react-bootstrap';

function CustomerType() {
  const [customerTypes, setCustomerTypes] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingCustomerType, setEditingCustomerType] = useState(null); // Track which customer type is being edited
  const [newCustomerType, setNewCustomerType] = useState({ customerTypeNo: '', name: '' });

  // Fetch customer types on component mount
  useEffect(() => {
    fetchCustomerTypes();
  }, []);

  // Fetch customer types from API
  const fetchCustomerTypes = () => {
    fetch('http://localhost:8082/api/customer-types')
      .then((response) => response.json())
      .then((data) => setCustomerTypes(data))
      .catch((error) => console.error('Error fetching customer types:', error));
  };

  // Handle delete action
  const handleDelete = (customerTypeNo) => {
    fetch(`http://localhost:8082/api/customer-types/${customerTypeNo}`, {
      method: 'DELETE',
    })
      .then(() => fetchCustomerTypes()) // Refresh customer types after deletion
      .catch((error) => console.error('Error deleting customer type:', error));
  };

  // Handle form submission (create or update)
  const handleSubmit = (e) => {
    e.preventDefault();

    const url = editingCustomerType
      ? `http://localhost:8082/api/customer-types/${editingCustomerType.customerTypeNo}`
      : 'http://localhost:8082/api/customer-types';

    const method = editingCustomerType ? 'PUT' : 'POST';

    fetch(url, {
      method: method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newCustomerType),
    })
      .then(() => {
        fetchCustomerTypes();
        handleCloseModal(); // Close modal and reset form
      })
      .catch((error) => console.error('Error submitting customer type:', error));
  };

  // Handle edit button click
  const handleEdit = (customerType) => {
    setEditingCustomerType(customerType); // Set editing customer type
    setNewCustomerType({
      customerTypeNo: customerType.customerTypeNo,
      name: customerType.name,
    });
    setShowModal(true); // Show the modal for editing
  };

  // Close modal and reset form
  const handleCloseModal = () => {
    setShowModal(false);
    setEditingCustomerType(null); // Reset editing state
    setNewCustomerType({ customerTypeNo: '', name: '' }); // Reset form
  };

  return (
    <Card>
      <Card.Header>
        <div className="d-flex justify-content-between align-items-center">
          <h4>Customer Types</h4>
          <Button variant="primary" onClick={() => setShowModal(true)}>
            + Add New Customer Type
          </Button>
        </div>
      </Card.Header>
      <Card.Body>
        <Accordion>
          {customerTypes.map((customerType, index) => (
            <Accordion.Item eventKey={index.toString()} key={index}>
              <Accordion.Header>
                {customerType.customerTypeNo} - {customerType.name}
              </Accordion.Header>
              <Accordion.Body>
                <div className="d-flex justify-content-between align-items-center">
                  <p>Details for {customerType.customerTypeNo}</p>
                  <div>
                    <Button
                      variant="warning"
                      className="me-2"
                      onClick={() => handleEdit(customerType)}
                    >
                      Edit
                    </Button>
                    <Button
                      variant="danger"
                      className="me-2"
                      onClick={() => handleDelete(customerType.customerTypeNo)}
                    >
                      Delete
                    </Button>
                  </div>
                </div>
              </Accordion.Body>
            </Accordion.Item>
          ))}
        </Accordion>
      </Card.Body>

      {/* Modal for Adding/Editing Customer Type */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>{editingCustomerType ? 'Edit Customer Type' : 'Add New Customer Type'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="customerTypeNo">
              <Form.Label>Customer Type No</Form.Label>
              <Form.Control
                type="text"
                value={newCustomerType.customerTypeNo}
                onChange={(e) => setNewCustomerType({ ...newCustomerType, customerTypeNo: e.target.value })}
                required
                disabled={!!editingCustomerType} // Disable when editing
              />
            </Form.Group>
            <Form.Group controlId="customerTypeName" className="mt-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                value={newCustomerType.name}
                onChange={(e) => setNewCustomerType({ ...newCustomerType, name: e.target.value })}
                required
              />
            </Form.Group>
            <Button variant="primary" type="submit" className="mt-3">
              {editingCustomerType ? 'Update Customer Type' : 'Save Customer Type'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </Card>
  );
}

export default CustomerType;
