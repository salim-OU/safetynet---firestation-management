package com.safetynet.controller;

import com.safetynet.dto.FireDTO;
import com.safetynet.model.FireStation;
import com.safetynet.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FireStationController {
    private final FireStationService fireStationService;

    @GetMapping("/firestations")
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        return ResponseEntity.ok(fireStationService.getAllFireStations());
    }

    @GetMapping("/firestations/station/{station}")
    public ResponseEntity<List<FireStation>> getFireStationsByStation(@PathVariable String station) {
        return ResponseEntity.ok(fireStationService.getFireStationsByStation(station));
    }

    @GetMapping("/firestations/address/{address}")
    public ResponseEntity<FireStation> getFireStationByAddress(@PathVariable String address) {
        return ResponseEntity.ok(fireStationService.getFireStationByAddress(address));
    }

    @PostMapping("/firestations")
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
        FireStation created = fireStationService.createFireStation(fireStation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/firestations/{address}")
    public ResponseEntity<FireStation> updateFireStation(
            @PathVariable String address,
            @RequestBody FireStation fireStation) {
        return ResponseEntity.ok(fireStationService.updateFireStation(address, fireStation));
    }

    @DeleteMapping("/firestations/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable String address) {
        fireStationService.deleteFireStation(address);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/firestations/addresses/{station}")
    public ResponseEntity<List<String>> getAddressesByStation(@PathVariable String station) {
        return ResponseEntity.ok(fireStationService.getAddressesByStation(station));
    }

    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getResidentsByAddress(@RequestParam String address) {
        return ResponseEntity.ok(fireStationService.getResidentsByAddress(address));
    }
}