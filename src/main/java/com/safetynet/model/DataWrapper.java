package com.safetynet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataWrapper {
    private List<Person> persons;
    private List<MedicalRecord> medicalrecords;
    private List<FireStation> firestations;
}