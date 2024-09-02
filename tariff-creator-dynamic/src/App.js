import React, { useState, useEffect } from 'react';
import { Container, Navbar, Nav, Button, Form, Table, Modal, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  const [tariffs, setTariffs] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showOperationModal, setShowOperationModal] = useState(false);
  const [editingTariff, setEditingTariff] = useState(null);
  const [selectedTariff, setSelectedTariff] = useState(null);
  const [newTariff, setNewTariff] = useState({ tarifeNo: '', name: '', details: [{ startTime: '', endTime: '', price: '' }] });
  const [operation, setOperation] = useState('add');
  const [operationValue, setOperationValue] = useState('');
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    fetchTariffs();
  }, []);

  const fetchTariffs = () => {
    fetch('http://localhost:8080/api/v1/tariffs')
      .then(response => response.json())
      .then(data => setTariffs(data))
      .catch(error => console.error('Error fetching tariffs:', error));
  };

  const handleAddDetail = () => {
    setNewTariff({
      ...newTariff,
      details: [...newTariff.details, { startTime: '', endTime: '', price: '' }],
    });
  };

  const handleDetailChange = (index, field, value) => {
    const newDetails = newTariff.details.map((detail, i) =>
      i === index ? { ...detail, [field]: value } : detail
    );
    setNewTariff({ ...newTariff, details: newDetails });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (editingTariff) {
      fetch(`http://localhost:8080/api/v1/tariffs/tarifeNo/${editingTariff.tarifeNo}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTariff),
      })
        .then(() => {
          fetchTariffs();
          setShowModal(false);
          setEditingTariff(null);
        })
        .catch((error) => console.error('Error updating tariff:', error));
    } else {
      fetch('http://localhost:8080/api/v1/tariffs', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTariff),
      })
        .then(() => {
          fetchTariffs();
          setShowModal(false);
        })
        .catch((error) => console.error('Error creating tariff:', error));
    }

    setNewTariff({ tarifeNo: '', name: '', details: [{ startTime: '', endTime: '', price: '' }] });
  };

  const handleEdit = (tariff) => {
    setEditingTariff(tariff);
    setNewTariff({
      tarifeNo: tariff.tarifeNo,
      name: tariff.name,
      details: tariff.details,
    });
    setShowModal(true);
  };

  const handleDelete = (tarifeNo) => {
    fetch(`http://localhost:8080/api/v1/tariffs/tarifeNo/${tarifeNo}`, {
      method: 'DELETE',
    })
      .then(() => fetchTariffs())
      .catch((error) => console.error('Error deleting tariff:', error));
  };

  const handleSearch = (e) => {
    setSearchQuery(e.target.value);
    if (e.target.value.trim() === '') {
      fetchTariffs();
    } else {
      fetch(`http://localhost:8080/api/v1/tariffs/name/${e.target.value}`)
        .then(response => response.json())
        .then(data => setTariffs(data))
        .catch(error => console.error('Error searching tariffs:', error));
    }
  };

  const handleOperationSubmit = () => {
    if (!selectedTariff) return;

    fetch(`http://localhost:8081/api/v1/tariff-operations/tarifeNo/${selectedTariff.tarifeNo}/modify-price`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        operation: operation,
        value: parseFloat(operationValue),
      }),
    })
      .then(() => {
        fetchTariffs();
        setShowOperationModal(false);
      })
      .catch((error) => console.error('Error performing operation on tariff:', error));
  };

  return (
    <div className="App">
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="#">Enterprise Tariff Manager</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="#home">Home</Nav.Link>
            </Nav>
            <Form className="d-flex">
              <Form.Control
                type="search"
                placeholder="Search tariffs by name"
                className="me-2"
                aria-label="Search"
                value={searchQuery}
                onChange={handleSearch}
              />
              <Button variant="outline-success">Search</Button>
            </Form>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container className="mt-5">
        <Card>
          <Card.Header>
            <div className="d-flex justify-content-between align-items-center">
              <h4>Tariffs List</h4>
              <div>
                <Button variant="primary" onClick={() => setShowModal(true)}>
                  + Add New Tariff
                </Button>
              </div>
            </div>
          </Card.Header>
          <Card.Body>
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Tariff No</th>
                  <th>Name</th>
                  <th>Details</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {tariffs.map((tariff, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{tariff.tarifeNo}</td>
                    <td>{tariff.name}</td>
                    <td>
                      {tariff.details.map((detail, i) => (
                        <div key={i}>
                          {detail.startTime} - {detail.endTime} mins: ${detail.price}
                        </div>
                      ))}
                    </td>
                    <td>
                      <Button variant="warning" className="me-2" onClick={() => handleEdit(tariff)}>
                        Edit
                      </Button>
                      <Button variant="danger" className="me-2" onClick={() => handleDelete(tariff.tarifeNo)}>
                        Delete
                      </Button>
                      <Button variant="info" onClick={() => { setSelectedTariff(tariff); setShowOperationModal(true); }}>
                        Modify Price
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </Card.Body>
        </Card>
      </Container>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>{editingTariff ? 'Edit Tariff' : 'Add New Tariff'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="formTariffNo">
              <Form.Label>Tariff No</Form.Label>
              <Form.Control
                type="text"
                value={newTariff.tarifeNo}
                onChange={(e) => setNewTariff({ ...newTariff, tarifeNo: e.target.value })}
                required
                disabled={!!editingTariff}
              />
            </Form.Group>
            <Form.Group controlId="formTariffName" className="mt-3">
              <Form.Label>Tariff Name</Form.Label>
              <Form.Control
                type="text"
                value={newTariff.name}
                onChange={(e) => setNewTariff({ ...newTariff, name: e.target.value })}
                required
              />
            </Form.Group>

            {newTariff.details.map((detail, index) => (
              <div key={index} className="mt-3">
                <h6>Detail {index + 1}</h6>
                <Form.Group controlId={`formStartTime-${index}`}>
                  <Form.Label>Start Time (min)</Form.Label>
                  <Form.Control
                    type="number"
                    placeholder="Start Time"
                    value={detail.startTime}
                    onChange={(e) => handleDetailChange(index, 'startTime', e.target.value)}
                    required
                  />
                </Form.Group>
                <Form.Group controlId={`formEndTime-${index}`} className="mt-2">
                  <Form.Label>End Time (min)</Form.Label>
                  <Form.Control
                    type="number"
                    placeholder="End Time"
                    value={detail.endTime}
                    onChange={(e) => handleDetailChange(index, 'endTime', e.target.value)}
                    required
                  />
                </Form.Group>
                <Form.Group controlId={`formPrice-${index}`} className="mt-2">
                  <Form.Label>Price</Form.Label>
                  <Form.Control
                    type="number"
                    placeholder="Price"
                    value={detail.price}
                    onChange={(e) => handleDetailChange(index, 'price', e.target.value)}
                    required
                  />
                </Form.Group>
              </div>
            ))}

            <Button variant="secondary" onClick={handleAddDetail} className="mt-3">
              Add Time Interval
            </Button>
            <Button variant="primary" type="submit" className="mt-3 ms-3">
              {editingTariff ? 'Update Tariff' : 'Save Tariff'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>

      <Modal show={showOperationModal} onHide={() => setShowOperationModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Modify Tariff Price</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formOperation">
              <Form.Label>Operation</Form.Label>
              <Form.Control
                as="select"
                value={operation}
                onChange={(e) => setOperation(e.target.value)}
              >
                <option value="add">Add</option>
                <option value="subtract">Subtract</option>
                <option value="multiply">Multiply</option>
                <option value="percentage">Percentage</option>
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="formOperationValue" className="mt-3">
              <Form.Label>Value</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter value"
                value={operationValue}
                onChange={(e) => setOperationValue(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowOperationModal(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleOperationSubmit}>
            Apply Operation
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default App;
