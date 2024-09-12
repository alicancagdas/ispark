import React, { useState, useEffect } from 'react';
import { Button, Form, Row, Col, Card, Accordion, Spinner, Alert } from 'react-bootstrap';

function UpperPackageCreator() {
  const [packages, setPackages] = useState([]);
  const [selectedPackages, setSelectedPackages] = useState([]);
  const [upperPackageName, setUpperPackageName] = useState('');
  const [upperPackageDescription, setUpperPackageDescription] = useState('');
  const [upperPackageNo, setUpperPackageNo] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false); // Yeni state: İstek yapılıyor mu?
  const [loadingPackages, setLoadingPackages] = useState(true); // Paketler yükleniyor mu?
  const [errorMessage, setErrorMessage] = useState(''); // Hata mesajı
  const [successMessage, setSuccessMessage] = useState(''); // Başarı mesajı

  // Mevcut paketleri fetch etmek
  useEffect(() => {
    fetchPackages();
  }, []);

  const fetchPackages = () => {
    fetch('http://localhost:8085/api/parking-packages') // package-creator-service'ten paketleri çekiyoruz
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setPackages(data);
        setLoadingPackages(false); // Paketler yüklendiğinde loading durumu kapat
      })
      .catch((error) => {
        console.error('Paketler yüklenirken hata:', error);
        setErrorMessage('Paketler yüklenirken bir hata oluştu.');
        setLoadingPackages(false); // Hata durumunda da loading durumu kapat
      });
  };

  // Paket seçimini yönetmek
  const handlePackageSelect = (packageId) => {
    if (selectedPackages.includes(packageId)) {
      setSelectedPackages(selectedPackages.filter((id) => id !== packageId));
    } else {
      setSelectedPackages([...selectedPackages, packageId]);
    }
  };

  // Upper package oluşturma
  const handleAddUpperPackage = () => {
    if (upperPackageName && upperPackageDescription && upperPackageNo && selectedPackages.length > 0) {
      // Seçilen paketler için SubPackageInfo nesneleri oluşturuyoruz
      const subPackageInfos = selectedPackages.map(pkgId => {
        const pkg = packages.find(p => p.id === pkgId);
        return {
          subPackageId: pkg.id,
          subPackageName: pkg.packageName,
          subPackageDescription: pkg.packageDescription,
        };
      });

      const upperPackageData = {
        upperPackageName,
        upperPackageDescription,
        upperPackageNo,
        upperSubPackages: subPackageInfos, // SubPackageInfo nesneleri
      };

      setIsSubmitting(true); // İstek başlatıldığında butonu devre dışı bırakıyoruz
      setErrorMessage(''); // Eski hata mesajını sıfırlıyoruz
      setSuccessMessage(''); // Eski başarı mesajını sıfırlıyoruz

      // Backend'e POST isteği gönderiyoruz
      fetch('http://localhost:8086/api/upper-packages', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(upperPackageData),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
        })
        .then((data) => {
          // Temizleme ve başarı mesajı gösterme
          setUpperPackageName('');
          setUpperPackageDescription('');
          setUpperPackageNo('');
          setSelectedPackages([]);
          setSuccessMessage('Upper Package başarıyla oluşturuldu!');
        })
        .catch((error) => {
          setErrorMessage('Upper Package oluşturulurken bir hata oluştu, lütfen tekrar deneyin.');
        })
        .finally(() => {
          setIsSubmitting(false); // İstek tamamlandığında butonu tekrar aktif hale getiriyoruz
        });
    } else {
      setErrorMessage('Lütfen tüm alanları doldurun ve en az bir paket seçin.');
    }
  };

  return (
    <div>
      <Form>
        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Upper Package Name</Form.Label>
              <Form.Control
                type="text"
                value={upperPackageName}
                onChange={(e) => setUpperPackageName(e.target.value)}
                placeholder="Enter upper package name"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group>
              <Form.Label>Upper Package Description</Form.Label>
              <Form.Control
                type="text"
                value={upperPackageDescription}
                onChange={(e) => setUpperPackageDescription(e.target.value)}
                placeholder="Enter upper package description"
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group>
              <Form.Label>Upper Package Number</Form.Label>
              <Form.Control
                type="text"
                value={upperPackageNo}
                onChange={(e) => setUpperPackageNo(e.target.value)}
                placeholder="Enter upper package number"
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Seçilen Paketler */}
        <h5>Select Packages to Include in Upper Package</h5>
        {loadingPackages ? (
          <Spinner animation="border" variant="primary" />
        ) : packages.length > 0 ? (
          <div className="mb-3">
            {packages.map((pkg) => (
              <Card key={pkg.id} className="mb-2">
                <Card.Body>
                  <strong>Paket Numarası: {pkg.id}</strong> {/* Paket numarası burada görünecek */}
                  <p>{pkg.packageName}</p> {/* Paket ismi */}
                  <p>{pkg.packageDescription}</p> {/* Paket açıklaması */}
                  <Accordion>
                    <Accordion.Item eventKey="0">
                      <Accordion.Header>Package Details</Accordion.Header>
                      <Accordion.Body>
                        <ul>
                          <li>Customer Type: {pkg.customerTypeName}</li>
                          {pkg.vehicleTypes.map((vehicle, i) => (
                            <li key={i}>
                              {vehicle.vehicleTypeName} - Tariffs: {vehicle.tariffs.map(t => t.tariffName).join(', ')}
                            </li>
                          ))}
                        </ul>
                      </Accordion.Body>
                    </Accordion.Item>
                  </Accordion>
                  <Form.Check
                    type="checkbox"
                    label="Select this package"
                    checked={selectedPackages.includes(pkg.id)}
                    onChange={() => handlePackageSelect(pkg.id)}
                  />
                </Card.Body>
              </Card>
            ))}
          </div>
        ) : (
          <p>Hiç paket bulunamadı.</p>
        )}

        {/* Hata ve Başarı Mesajları */}
        {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
        {successMessage && <Alert variant="success">{successMessage}</Alert>}

        {/* Upper Package Kaydetme Butonu */}
        <Button
          variant="primary"
          className="w-100 mt-3"
          onClick={handleAddUpperPackage}
          disabled={isSubmitting} // Buton tıklanmayı devre dışı bırakılırken isteğin durumu gösteriliyor
        >
          {isSubmitting ? 'Creating...' : 'Create Upper Package'}
        </Button>
      </Form>
    </div>
  );
}

export default UpperPackageCreator;
