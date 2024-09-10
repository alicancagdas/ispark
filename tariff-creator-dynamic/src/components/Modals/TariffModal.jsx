// src/components/Modals/TariffModal.jsx
import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

const TariffModal = ({ show, handleClose, handleSubmit, tariff = {}, handleDetailChange, addDetail }) => {
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>{tariff?.tarifeNo ? 'Edit Tariff' : 'Add New Tariff'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="formTariffNo">
            <Form.Label>Tariff No</Form.Label>
            <Form.Control
              type="text"
              value={tariff?.tarifeNo || ''}
              onChange={(e) => handleDetailChange('tarifeNo', e.target.value)}
              required
              disabled={!!tariff?.tarifeNo} // Disable input if editing
            />
          </Form.Group>

          <Form.Group controlId="formTariffName" className="mt-3">
            <Form.Label>Tariff Name</Form.Label>
            <Form.Control
              type="text"
              value={tariff?.name || ''}
              onChange={(e) => handleDetailChange('name', e.target.value)}
              required
            />
          </Form.Group>

          {tariff?.details?.map((detail, index) => (
            <div key={index} className="mt-3">
              <Form.Group controlId={`formStartTime-${index}`}>
                <Form.Label>Start Time (min)</Form.Label>
                <Form.Control
                  type="number"
                  value={detail.startTime || ''}
                  onChange={(e) => handleDetailChange('startTime', e.target.value, index)}
                  required
                />
              </Form.Group>
              <Form.Group controlId={`formEndTime-${index}`} className="mt-2">
                <Form.Label>End Time (min)</Form.Label>
                <Form.Control
                  type="number"
                  value={detail.endTime || ''}
                  onChange={(e) => handleDetailChange('endTime', e.target.value, index)}
                  required
                />
              </Form.Group>
              <Form.Group controlId={`formPrice-${index}`} className="mt-2">
                <Form.Label>Price</Form.Label>
                <Form.Control
                  type="number"
                  value={detail.price || ''}
                  onChange={(e) => handleDetailChange('price', e.target.value, index)}
                  required
                />
              </Form.Group>
            </div>
          ))}

          <Button variant="secondary" onClick={addDetail} className="mt-3">
            Add Time Interval
          </Button>

          <Button variant="primary" type="submit" className="mt-3 ms-3">
            {tariff?.tarifeNo ? 'Update Tariff' : 'Save Tariff'}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default TariffModal;
