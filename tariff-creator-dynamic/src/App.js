import React from 'react';
import { Container, Navbar, Nav, Tabs, Tab, Row, Col, Card } from 'react-bootstrap';
import { FaBell, FaUser, FaSearch } from 'react-icons/fa';
import Tariff from './components/Tariff';
import VehicleType from './components/VehicleType';
import CustomerType from './components/CustomerType';
import LocationComponent from './components/LocationComponent';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'; // Özel stil dosyaları

function App() {
  return (
    <div className="App bg-light min-h-screen">
      {/* Navbar */}
      <Navbar bg="dark" variant="dark" expand="lg" className="shadow-lg py-3 fixed-top">
        <Container>
          <Navbar.Brand href="#" className="fw-bold text-warning">İSPARK Dashboard</Navbar.Brand>
          <Navbar.Toggle aria-controls="navbarResponsive" />
          <Navbar.Collapse id="navbarResponsive">
            <Nav className="me-auto">
              <Nav.Link href="#home" className="text-white">Home</Nav.Link>
              <Nav.Link href="#dashboard" className="text-white">Dashboard</Nav.Link>
            </Nav>
            {/* Sağ taraftaki arama çubuğu ve kullanıcı menüsü */}
            <Nav className="ms-auto">
              <Nav.Item className="me-3">
                <FaSearch className="text-white" />
              </Nav.Item>
              <Nav.Item className="me-3">
                <FaBell className="text-white" />
              </Nav.Item>
              <Nav.Item className="me-3">
                <FaUser className="text-white" />
              </Nav.Item>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      {/* Hero Banner */}
      <div className="w-full h-80 bg-cover bg-center" style={{ backgroundImage: `url('https://example.com/hero-image.jpg')` }}>
        <div className="bg-dark bg-opacity-50 h-100 d-flex justify-content-center align-items-center">
          <h1 className="text-white text-center display-4 animate__animated animate__fadeIn">İSPARK Dashboard</h1>
        </div>
      </div>

      {/* Ana İçerik */}
      <Container className="mt-5 pt-5">
        <Tabs defaultActiveKey="tariffs" id="dashboard-tabs" className="mb-5 border-0" justify transition>
          {/* Tariffs Tab - Tam genişlikte içerik */}
          <Tab eventKey="tariffs" title="Tariffs">
            <Row className="mb-4">
              <Col>
                <h2 className="mb-4" style={{ color: '#1E3A8A' }}>Manage Tariffs</h2>
              </Col>
            </Row>

            {/* Tariff Management İçeriği - Tam genişlikte */}
            <Row>
              <Col>
                <Card className="mb-4 shadow-lg animate-card-hover" style={{ backgroundColor: '#F59E0B', color: '#1E3A8A' }}>
                  <Card.Body>
                    <Card.Title className="text-dark">Tariff Management</Card.Title>
                    <Card.Text>
                      Adjust, add or delete tariffs for different parking services. You can manage all tariff settings and adjustments here.
                    </Card.Text>
                    {/* Tariff Yönetimi bileşeni geniş alanda */}
                    <Tariff />
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </Tab>

          {/* Vehicle Types Tab */}
          <Tab eventKey="vehicle-types" title="Vehicle Types">
            <Row className="mb-4">
              <Col>
                <h2 className="mb-4" style={{ color: '#F59E0B' }}>Manage Vehicle Types</h2>
              </Col>
            </Row>
            <Row>
              <Col>
                <Card className="mb-4 shadow-lg animate-card-hover" style={{ backgroundColor: '#93C5FD', color: '#1E3A8A' }}>
                  <Card.Body>
                    <Card.Title className="text-dark">Vehicle Types Management</Card.Title>
                    <Card.Text>
                      Define, update, and organize various vehicle categories for different parking zones.
                    </Card.Text>
                    <VehicleType />
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </Tab>

          {/* Customer Types Tab */}
          <Tab eventKey="customer-types" title="Customer Types">
            <Row className="mb-4">
              <Col>
                <h2 className="mb-4" style={{ color: '#20C997' }}>Manage Customer Types</h2>
              </Col>
            </Row>
            <Row>
              <Col>
                <Card className="mb-4 shadow-lg animate-card-hover" style={{ backgroundColor: '#E5E7EB', color: '#1E3A8A' }}>
                  <Card.Body>
                    <Card.Title className="text-dark">Customer Management</Card.Title>
                    <Card.Text>
                      Manage customer types, preferences, and related parking services efficiently.
                    </Card.Text>
                    <CustomerType />
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </Tab>

          {/* Location Management Tab */}
          <Tab eventKey="location-management" title="Location Management">
            <Row className="mb-4">
              <Col>
                <h2 className="mb-4" style={{ color: '#1E3A8A' }}>Manage Locations</h2>
              </Col>
            </Row>
            <Row>
              <Col>
                <Card className="mb-4 shadow-lg animate-card-hover" style={{ backgroundColor: '#93C5FD', color: '#1E3A8A' }}>
                  <Card.Body>
                    <Card.Title className="text-dark">Location Management</Card.Title>
                    <Card.Text>
                      Manage parking lots, facilities, and their availability across different zones.
                    </Card.Text>
                    <LocationComponent />
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </Tab>
        </Tabs>
      </Container>
    </div>
  );
}

export default App;
