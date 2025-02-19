package com.safetynet.service;

import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepository;
import com.safetynet.exception.AlertApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.findByName(firstName, lastName)
                .orElseThrow(() -> new AlertApiException(
                        "Medical record not found for: " + firstName + " " + lastName,
                        HttpStatus.NOT_FOUND
                ));
    }

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        medicalRecordRepository.findByName(firstName, lastName)
                .orElseThrow(() -> new AlertApiException(
                        "Medical record not found for update: " + firstName + " " + lastName,
                        HttpStatus.NOT_FOUND
                ));

        return medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        if (!medicalRecordRepository.delete(firstName, lastName)) {
            throw new AlertApiException(
                    "Medical record not found for deletion: " + firstName + " " + lastName,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}