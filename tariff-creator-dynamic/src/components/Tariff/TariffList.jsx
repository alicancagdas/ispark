// src/components/Tariff/TariffList.jsx
import React from 'react';
import { Table, Button } from 'react-bootstrap';

const TariffList = ({ tariffs, onEdit, onDelete, onModify }) => {
  return (
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
            <td className="tariff-name">{tariff.name}</td>
            <td className="details-column">
              {tariff.details.map((detail, i) => (
                <div key={i}>
                  {detail.startTime} - {detail.endTime} mins: ${detail.price}
                </div>
              ))}
            </td>
            <td className="table-actions">
              <Button variant="warning" onClick={() => onEdit(tariff)}>
                Edit
              </Button>
              <Button variant="danger" onClick={() => onDelete(tariff.tarifeNo)}>
                Delete
              </Button>
              <Button variant="info" onClick={() => onModify(tariff)}>
                Modify Price
              </Button>
            </td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
};

export default TariffList;
