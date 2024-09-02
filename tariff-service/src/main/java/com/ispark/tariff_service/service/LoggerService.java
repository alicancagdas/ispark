package com.ispark.tariff_service.service;

import com.ispark.tariff_service.model.LogEntry;
import com.ispark.tariff_service.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoggerService {

    @Autowired
    private LogRepository logRepository;

    public void logInfo(String message) {
        log("INFO", message);
    }

    public void logError(String message) {
        log("ERROR", message);
    }

    private void log(String level, String message) {
        LogEntry logEntry = new LogEntry(null, level, message, new Date());
        logRepository.save(logEntry);
    }
}
