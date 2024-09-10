// src/components/Navbar/CustomNavbar.jsx
import React from 'react';
import { Navbar, Nav, Container, Form, Button } from 'react-bootstrap';

const CustomNavbar = ({ searchQuery, onSearch }) => (
  <Navbar bg="dark" variant="dark" expand="lg" className="shadow-md">
    <Container>
      <Navbar.Brand href="#" className="text-xl font-semibold text-white">Enterprise Tariff Manager</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto">
          <Nav.Link href="#home" className="text-white">Home</Nav.Link>
        </Nav>
        <Form className="d-flex">
          <Form.Control
            type="search"
            placeholder="Search tariffs by name"
            className="me-2 text-sm"
            value={searchQuery}
            onChange={onSearch}
          />
          <Button variant="outline-success" className="text-sm">Search</Button>
        </Form>
      </Navbar.Collapse>
    </Container>
  </Navbar>
);

export default CustomNavbar;
