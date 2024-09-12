import React, { useState } from 'react';
import { Container, Navbar, Nav, Tabs, Tab } from 'react-bootstrap';
import { FaBell, FaUser, FaSearch } from 'react-icons/fa';
import Tariff from './components/Tariff';
import VehicleType from './components/VehicleType';
import CustomerType from './components/CustomerType';
import LocationComponent from './components/LocationComponent';
import PackageCreator from './components/PackageCreator';
import UpperPackageCreator from './components/UpperPackageCreator'; // UpperPackageCreator bileşeni
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'; // Custom CSS for additional styling

function App() {
  const [key, setKey] = useState('tariffs');
  const [selectedTariff, setSelectedTariff] = useState(null);

  const switchToTariffs = (tariffId) => {
    setSelectedTariff(tariffId);
    setKey('tariffs'); // Tariffs sekmesine geçiş yapar
  };

  return (
    <div className="App bg-light min-h-screen">
      <Navbar bg="dark" variant="dark" expand="lg" className="shadow-lg py-3 fixed-top">
        <Container>
          <Navbar.Brand href="#" className="fw-bold text-warning">İSPARK Dashboard</Navbar.Brand>
          <Navbar.Toggle aria-controls="navbarResponsive" />
          <Navbar.Collapse id="navbarResponsive">
            <Nav className="me-auto">
              <Nav.Link href="#home" className="text-white">Home</Nav.Link>
              <Nav.Link href="#dashboard" className="text-white">Dashboard</Nav.Link>
            </Nav>
            <Nav className="ms-auto d-flex align-items-center">
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

      {/* Tabs Section */}
      <Container className="mt-5 pt-5">
        <Tabs
          id="dashboard-tabs"
          activeKey={key}
          onSelect={(k) => setKey(k)}
          className="mb-5 border-0"
          justify
          transition
        >
          <Tab eventKey="tariffs" title={<span className="text-dark">Tariffs</span>}>
            <div className="p-4 bg-white shadow rounded">
              <Tariff selectedTariff={selectedTariff} />
            </div>
          </Tab>

          <Tab eventKey="vehicle-types" title={<span className="text-dark">Vehicle Types</span>}>
            <div className="p-4 bg-white shadow rounded">
              <VehicleType />
            </div>
          </Tab>

          <Tab eventKey="customer-types" title={<span className="text-dark">Customer Types</span>}>
            <div className="p-4 bg-white shadow rounded">
              <CustomerType />
            </div>
          </Tab>

          <Tab eventKey="location-management" title={<span className="text-dark">Location Management</span>}>
            <div className="p-4 bg-white shadow rounded">
              <LocationComponent />
            </div>
          </Tab>

          <Tab eventKey="package-creator" title={<span className="text-dark">Package Creator</span>}>
            <div className="p-4 bg-white shadow rounded">
              <PackageCreator switchToTariffs={switchToTariffs} />
            </div>
          </Tab>

          <Tab eventKey="upper-package-creator" title={<span className="text-dark">Upper Package Creator</span>}>
            <div className="p-4 bg-white shadow rounded">
              <UpperPackageCreator />
            </div>
          </Tab>
        </Tabs>
      </Container>
    </div>
  );
}

export default App;
