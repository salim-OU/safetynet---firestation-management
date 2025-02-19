package com.safetynet.repository;

import com.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicalRecordRepository {
    private final DataRepository dataRepository;
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    // Initialiser les données depuis DataRepository
    private void initializeData() {
        if (medicalRecords.isEmpty()) {
            medicalRecords = new ArrayList<>(dataRepository.findAllMedicalRecords());
        }
    }

    public List<MedicalRecord> findAll() {
        initializeData();
        return new ArrayList<>(medicalRecords);
    }

    public Optional<MedicalRecord> findByName(String firstName, String lastName) {
        initializeData();
        return medicalRecords.stream()
                .filter(record -> record.getFirstName().equals(firstName)
                        && record.getLastName().equals(lastName))
                .findFirst();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        initializeData();
        medicalRecords.removeIf(record ->
                record.getFirstName().equals(medicalRecord.getFirstName())
                        && record.getLastName().equals(medicalRecord.getLastName())
        );
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    public boolean delete(String firstName, String lastName) {
        initializeData();
        return medicalRecords.removeIf(record ->
                record.getFirstName().equals(firstName)
                        && record.getLastName().equals(lastName)
        );
    }

    public void clear() {
        medicalRecords.clear();
    }

    public void addAll(List<MedicalRecord> records) {
        medicalRecords.addAll(records);
    }
}