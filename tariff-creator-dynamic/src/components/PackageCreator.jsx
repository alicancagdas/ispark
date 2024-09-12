import React, { useState, useEffect } from 'react';
import { Button, Form, Row, Col, Dropdown, Card, Accordion } from 'react-bootstrap';

function PackageCreator({ switchToTariffs }) {
  const [vehicleTypes, setVehicleTypes] = useState([]);
  const [tariffs, setTariffs] = useState([]);
  const [customerTypes, setCustomerTypes] = useState([]);
  const [selectedCustomerType, setSelectedCustomerType] = useState(null);
  const [isCustomerTypeLocked, setIsCustomerTypeLocked] = useState(false);  // Yeni state
  const [selectedVehicleTypes, setSelectedVehicleTypes] = useState([]);
  const [currentVehicleType, setCurrentVehicleType] = useState(null);
  const [selectedTariffs, setSelectedTariffs] = useState([]);
  const [packages, setPackages] = useState([]);
  const [packageName, setPackageName] = useState('');
  const [packageDescription, setPackageDescription] = useState('');

  useEffect(() => {
    fetchVehicleTypes();
    fetchTariffs();
    fetchCustomerTypes();
    fetchPackages();  // Mevcut paketleri çek
  }, []);

  const fetchVehicleTypes = () => {
    fetch('http://localhost:8083/api/vehicle-types')
      .then((response) => response.json())
      .then((data) => setVehicleTypes(data))
      .catch((error) => console.error('Error fetching vehicle types:', error));
  };

  const fetchTariffs = () => {
    fetch('http://localhost:8080/api/v1/tariffs')
      .then((response) => response.json())
      .then((data) => setTariffs(data))
      .catch((error) => console.error('Error fetching tariffs:', error));
  };

  const fetchCustomerTypes = () => {
    fetch('http://localhost:8082/api/customer-types')
      .then((response) => response.json())
      .then((data) => setCustomerTypes(data))
      .catch((error) => console.error('Error fetching customer types:', error));
  };

  const fetchPackages = () => {
    fetch('http://localhost:8085/api/parking-packages')
      .then((response) => response.json())
      .then((data) => setPackages(data))  // Mevcut paketler state'e kaydediliyor
      .catch((error) => console.error('Error fetching packages:', error));
  };

  const handleAddVehicleType = () => {
    if (currentVehicleType && selectedTariffs.length > 0) {
      const alreadyAdded = selectedVehicleTypes.some(
        (vehicle) => vehicle.vehicleType === currentVehicleType
      );

      if (!alreadyAdded) {
        const vehicleWithTariffs = {
          vehicleType: currentVehicleType,
          tariffs: selectedTariffs,
        };
        setSelectedVehicleTypes([...selectedVehicleTypes, vehicleWithTariffs]);
        setCurrentVehicleType(null);
        setSelectedTariffs([]);
      } else {
        alert('Bu araç tipi zaten eklenmiş!');
      }
    }
  };

  const handleAddPackage = () => {
    if (selectedCustomerType && selectedVehicleTypes.length > 0 && packageName && packageDescription) {
      const packageData = {
        packageName,
        packageDescription,
        customerTypeNo: selectedCustomerType,
        customerTypeName: customerTypes.find(c => c.customerTypeNo === selectedCustomerType)?.name,
        vehicleTypes: selectedVehicleTypes.map(vehicle => ({
          vehicleTypeNo: vehicle.vehicleType,
          vehicleTypeName: vehicleTypes.find(v => v.typeNo === vehicle.vehicleType)?.name,
          tariffs: vehicle.tariffs.map(tariffId => ({
            tariffNo: tariffId,
            tariffName: tariffs.find(t => t.tarifeNo === tariffId)?.name,
          }))
        }))
      };

      // Backend'e POST isteği gönderiyoruz
      fetch('http://localhost:8085/api/parking-packages', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(packageData),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log('Paket kaydedildi:', data);
          setPackages([...packages, data]); // Yeni paketi mevcut paketler listesine ekliyoruz
          setIsCustomerTypeLocked(false); // Paket oluşturulduktan sonra yeniden seçim yapılabilir
        })
        .catch((error) => {
          console.error('Error saving package:', error);
        });

      setSelectedVehicleTypes([]);
      setSelectedCustomerType(null);
      setPackageName('');
      setPackageDescription('');
    } else {
      alert('Lütfen tüm alanları doldurun.');
    }
  };

  const handleDeletePackage = (packageId) => {
    fetch(`http://localhost:8085/api/parking-packages/${packageId}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          // API çağrısı başarılıysa, paketi listeden çıkar
          setPackages(packages.filter((pkg) => pkg.id !== packageId));
        } else {
          console.error('Error deleting package:', response.statusText);
        }
      })
      .catch((error) => {
        console.error('Error deleting package:', error);
      });
  };

  const handleTariffClick = (tariffId) => {
    switchToTariffs(tariffId);
  };

  const handleCustomerTypeSelect = (key) => {
    setSelectedCustomerType(key);
    setIsCustomerTypeLocked(true); // Müşteri tipi seçildikten sonra kilitlenir
  };

  return (
    <div>
      <Form>
        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Package Name</Form.Label>
              <Form.Control
                type="text"
                value={packageName}
                onChange={(e) => setPackageName(e.target.value)}
                placeholder="Enter package name"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group>
              <Form.Label>Package Description</Form.Label>
              <Form.Control
                type="text"
                value={packageDescription}
                onChange={(e) => setPackageDescription(e.target.value)}
                placeholder="Enter package description"
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Customer Type Selection */}
        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Customer Type</Form.Label>
              <Dropdown onSelect={handleCustomerTypeSelect}>
                <Dropdown.Toggle
                  variant="light"
                  className="w-100 text-start"
                  disabled={isCustomerTypeLocked}  // Müşteri tipi kilitlendiğinde seçilemez
                >
                  {selectedCustomerType
                    ? customerTypes.find(c => c.customerTypeNo === selectedCustomerType)?.name || 'Loading...'
                    : 'Select Customer Type'}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  {customerTypes.length > 0 ? customerTypes.map((customerType) => (
                    <Dropdown.Item key={customerType.customerTypeNo} eventKey={customerType.customerTypeNo}>
                      {customerType.customerTypeNo} - {customerType.name}
                    </Dropdown.Item>
                  )) : <Dropdown.Item disabled>Loading customer types...</Dropdown.Item>}
                </Dropdown.Menu>
              </Dropdown>
            </Form.Group>
          </Col>
        </Row>

        {/* Vehicle Type and Tariffs Selection */}
        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Vehicle Type</Form.Label>
              <Dropdown onSelect={(key) => setCurrentVehicleType(key)}>
                <Dropdown.Toggle variant="light" className="w-100 text-start">
                  {currentVehicleType
                    ? vehicleTypes.find(v => v.typeNo === currentVehicleType)?.name || 'Loading...'
                    : 'Select Vehicle Type'}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  {vehicleTypes.length > 0 ? vehicleTypes.map((vehicle) => (
                    <Dropdown.Item key={vehicle.typeNo} eventKey={vehicle.typeNo}>
                      {vehicle.typeNo} - {vehicle.name}
                    </Dropdown.Item>
                  )) : <Dropdown.Item disabled>Loading vehicle types...</Dropdown.Item>}
                </Dropdown.Menu>
              </Dropdown>
            </Form.Group>
          </Col>
          <Col>
            <Form.Group>
              <Form.Label>Tariffs</Form.Label>
              <Dropdown onSelect={(key) => setSelectedTariffs([...selectedTariffs, key])}>
                <Dropdown.Toggle variant="light" className="w-100 text-start">
                  {selectedTariffs.length > 0
                    ? `${selectedTariffs.length} Selected`
                    : 'Select Tariffs'}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  {tariffs.length > 0 ? tariffs.map((tariff) => (
                    <Dropdown.Item key={tariff.tarifeNo} eventKey={tariff.tarifeNo}>
                      {tariff.tarifeNo} - {tariff.name}
                    </Dropdown.Item>
                  )) : <Dropdown.Item disabled>Loading tariffs...</Dropdown.Item>}
                </Dropdown.Menu>
              </Dropdown>
            </Form.Group>
          </Col>
        </Row>

        {/* Araç Tipi ve Tarife Ekle Butonu */}
        <Button variant="outline-primary" className="w-100 mb-3" onClick={handleAddVehicleType}>
          Add Vehicle Type & Tariffs
        </Button>

        {/* Seçilen Araç Tipleri ve Tarifeler */}
        {selectedVehicleTypes?.length > 0 && (
          <>
            <h6 className="text-muted mb-3">Selected Vehicle Types & Tariffs</h6>
            {selectedVehicleTypes.map((pkg, index) => (
              <Card key={index} className="mb-2">
                <Card.Body>
                  <strong>{vehicleTypes.find(v => v.typeNo === pkg.vehicleType)?.name || 'Loading...'}</strong>
                  <Accordion>
                    <Accordion.Item eventKey="0">
                      <Accordion.Header>View Tariffs</Accordion.Header>
                      <Accordion.Body>
                        <ul>
                          {pkg.tariffs?.map((tariffId, i) => {
                            const tariff = tariffs.find((t) => t.tarifeNo === tariffId);
                            return (
                              <li key={i}>
                                <Button variant="link" onClick={() => handleTariffClick(tariff?.tarifeNo)}>
                                  {tariff?.tarifeNo} - {tariff?.name}
                                </Button>
                              </li>
                            );
                          })}
                        </ul>
                      </Accordion.Body>
                    </Accordion.Item>
                  </Accordion>
                </Card.Body>
              </Card>
            ))}
          </>
        )}

        {/* Paket Kaydetme Butonu */}
        <Button variant="primary" className="w-100 mt-3" onClick={handleAddPackage}>
          Save Package
        </Button>

        {/* Oluşturulan Paketler */}
        {packages.length > 0 && (
          <>
            <h6 className="text-muted mt-5 mb-3">Created Packages</h6>
            {packages.map((pkg, index) => (
              <Card key={index} className="mb-2">
                <Card.Body>
                  <strong>Customer Type: {pkg.customerTypeName || 'Unknown'}</strong>
                  {pkg.vehicleTypes?.map((vehicle, i) => (
                    <div key={i}>
                      <strong>Vehicle Type: {vehicle.vehicleTypeName || 'Unknown'}</strong>
                      <Accordion>
                        <Accordion.Item eventKey="0">
                          <Accordion.Header>View Tariffs</Accordion.Header>
                          <Accordion.Body>
                            <ul>
                              {vehicle.tariffs?.map((tariff, j) => (
                                <li key={j}>
                                  <Button variant="link" onClick={() => handleTariffClick(tariff?.tariffNo)}>
                                    {tariff?.tariffNo} - {tariff?.tariffName}
                                  </Button>
                                </li>
                              ))}
                            </ul>
                          </Accordion.Body>
                        </Accordion.Item>
                      </Accordion>
                    </div>
                  ))}
                  <Button variant="outline-danger" className="mt-2" onClick={() => handleDeletePackage(pkg.id)}>
                    Delete Package
                  </Button>
                </Card.Body>
              </Card>
            ))}
          </>
        )}
      </Form>
    </div>
  );
}

export default PackageCreator;
