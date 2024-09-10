package com.ispark.tariff_service.service;

import com.ispark.tariff_service.model.Tariff;
import com.ispark.tariff_service.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TariffService {

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private LoggerService loggerService;

    public List<Tariff> getAllTariffs() {
        loggerService.logInfo("Fetching all tariffs");
        return tariffRepository.findAll();
    }

    public Tariff getTariffByTarifeNo(String tarifeNo) {
        loggerService.logInfo("Fetching tariff by TarifeNo: " + tarifeNo);
        return tariffRepository.findByTarifeNo(tarifeNo)
                .orElseThrow(() -> {
                    loggerService.logError("Tariff not found with TarifeNo: " + tarifeNo);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tariff not found with TarifeNo: " + tarifeNo);
                });
    }

    public Tariff createTariff(Tariff tariff) {
        // TariffFactory'yi kullanarak yeni bir Tariff oluşturuyoruz
        Tariff createdTariff = TariffFactory.createTariff(tariff, "Auto-generated");
        loggerService.logInfo("Creating new tariff with tarifeNo: " + createdTariff.getTarifeNo());

        // Tariff veritabanına kaydediliyor
        return tariffRepository.save(createdTariff);
    }
    // Method to duplicate a tariff and change its name
    // Method to duplicate a tariff and change its name
    public Tariff duplicateAndChangeName(Tariff originalTariff) {
        // Use the factory to create the duplicated tariff
        Tariff duplicatedTariff = TariffFactory.createTariff(originalTariff, "Duplicated from " + originalTariff.getTarifeNo());

        // Change the name to indicate duplication
        duplicatedTariff.setName("Copy of " + originalTariff.getName());

        // Save and log the operation
        Tariff savedTariff = tariffRepository.save(duplicatedTariff);
        loggerService.logInfo("Duplicated tariff with new TarifeNo: " + savedTariff.getTarifeNo());

        return savedTariff;
    }
    public Tariff updateTariffByTarifeNo(String tarifeNo, Tariff updatedTariff) {
        loggerService.logInfo("Updating tariff with TarifeNo: " + tarifeNo);

        Tariff existingTariff = tariffRepository.findByTarifeNo(tarifeNo)
                .orElseThrow(() -> {
                    loggerService.logError("Tariff not found with TarifeNo: " + tarifeNo);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Tariff not found with TarifeNo: " + tarifeNo);
                });

        // Mevcut tarifedeki bilgileri güncelle
        if (updatedTariff.getName() != null) {
            existingTariff.setName(updatedTariff.getName());
        }

        if (updatedTariff.getDetails() != null && !updatedTariff.getDetails().isEmpty()) {
            existingTariff.getDetails().clear();
            existingTariff.getDetails().addAll(updatedTariff.getDetails());
        }

        existingTariff.setUpdateDate(LocalDateTime.now());
        existingTariff.setLastUpdatedBy("Manual update on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        loggerService.logInfo("Tariff updated successfully with TarifeNo: " + existingTariff.getTarifeNo());

        return tariffRepository.save(existingTariff);
    }


    private void updateTariffDetails(Tariff tariff, Tariff updatedTariff) {
        tariff.setName(updatedTariff.getName());
        tariff.setDetails(updatedTariff.getDetails());
        tariff.setUpdateDate(LocalDateTime.now());  // Update date
        tariff.setLastUpdatedBy("Manual update on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public List<Tariff> getTariffByName(String name) {
        loggerService.logInfo("Fetching tariff(s) by name: " + name);
        List<Tariff> tariffs = tariffRepository.findByName(name);

        if (tariffs.isEmpty()) {
            loggerService.logError("No tariffs found with name: " + name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tariffs found with name: " + name);
        }

        return tariffs;
    }


    public void deleteTariffByTarifeNo(String tarifeNo) {
        loggerService.logInfo("Deleting tariff with TarifeNo: " + tarifeNo);
        Tariff tariff = tariffRepository.findByTarifeNo(tarifeNo)
                .orElseThrow(() -> {
                    loggerService.logError("Tariff not found with TarifeNo: " + tarifeNo);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tariff not found with TarifeNo: " + tarifeNo);
                });
        tariffRepository.delete(tariff);

        // Kafka'ya tarife silme mesajı gönder (isteğe bağlı)
    }

    public void deleteAllTariffs() {
        List<Tariff> allTariffs = tariffRepository.findAll();

        if (allTariffs.isEmpty()) {
            loggerService.logError("No tariffs found to delete.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tariffs found to delete.");
        }

        // Tariff'leri tek tek sil
        for (Tariff tariff : allTariffs) {
            tariffRepository.delete(tariff);
            loggerService.logInfo("Deleted tariff with TarifeNo: " + tariff.getTarifeNo());
        }
    }

    /**
     * Kafka'dan gelen tarifeyi yeni bir tarife olarak oluşturur ve veritabanına kaydeder.
     *
     * @param kafkaTariff Kafka'dan gelen tarife
     * @return Oluşturulan yeni tarife
     */
    public Tariff createTariffFromKafka(Tariff kafkaTariff) {
        // TariffFactory'yi kullanarak yeni bir Tariff oluşturuyoruz
        Tariff createdTariff = TariffFactory.createTariff(kafkaTariff, "Kafka Event");
        loggerService.logInfo("Creating new tariff from Kafka with tarifeNo: " + createdTariff.getTarifeNo());

        // Tariff veritabanına kaydediliyor
        return tariffRepository.save(createdTariff);
    }
}
