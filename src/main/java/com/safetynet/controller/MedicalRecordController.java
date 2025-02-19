package com.safetynet.controller;



import com.safetynet.model.MedicalRecord;
import com.safetynet.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{firstName}/{lastName}")
    public MedicalRecord getMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        return medicalRecordService.getMedicalRecord(firstName, lastName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/{firstName}/{lastName}")
    public MedicalRecord updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}