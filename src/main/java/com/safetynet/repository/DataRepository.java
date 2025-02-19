package com.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.DataWrapper;
import com.safetynet.model.FireStation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class DataRepository {
    private DataWrapper data;
    private final ObjectMapper objectMapper;

    public DataRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadData();
    }

    private void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("data/data.json");
            data = objectMapper.readValue(resource.getInputStream(), DataWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data.json", e);
        }
    }

    public List<Person> findAllPersons() {
        return data.getPersons();
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return data.getMedicalrecords();
    }

    public List<FireStation> findAllFireStations() {
        return data.getFirestations();
    }
}
