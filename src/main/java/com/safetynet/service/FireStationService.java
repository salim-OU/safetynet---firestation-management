package com.safetynet.service;
import com.safetynet.dto.FireDTO;
import com.safetynet.dto.PersonInfoDTO;
import com.safetynet.exception.AlertApiException;
import com.safetynet.model.FireStation;
import com.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FireStationService {
    private final FireStationRepository fireStationRepository;
    private final PersonService personService;
    public List<FireStation> getAllFireStations() {
        return fireStationRepository.findAll();
    }

    public List<FireStation> getFireStationsByStation(String station) {
        List<FireStation> stations = fireStationRepository.findByStation(station);
        if (stations.isEmpty()) {
            throw new AlertApiException(
                    "No fire stations found for station number: " + station,
                    HttpStatus.NOT_FOUND);
        }
        return stations;
    }

    public FireStation getFireStationByAddress(String address) {
        return fireStationRepository.findByAddress(address)
                .orElseThrow(() -> new AlertApiException(
                        "Fire station not found for address: " + address,
                        HttpStatus.NOT_FOUND));
    }

    public FireStation createFireStation(FireStation fireStation) {
        if (fireStation.getAddress() == null || fireStation.getAddress().trim().isEmpty()) {
            throw new AlertApiException("Address cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (fireStation.getStation() == null || fireStation.getStation().trim().isEmpty()) {
            throw new AlertApiException("Station number cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        return fireStationRepository.save(fireStation);
    }

    public FireStation updateFireStation(String address, FireStation fireStation) {
        if (!fireStationRepository.findByAddress(address).isPresent()) {
            throw new AlertApiException(
                    "Fire station not found for address: " + address,
                    HttpStatus.NOT_FOUND);
        }
        return fireStationRepository.save(fireStation);
    }

    public void deleteFireStation(String address) {
        if (!fireStationRepository.deleteByAddress(address)) {
            throw new AlertApiException(
                    "Fire station not found for address: " + address,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<String> getAddressesByStation(String station) {
        List<String> addresses = fireStationRepository.findAddressesByStation(station);
        if (addresses.isEmpty()) {
            throw new AlertApiException(
                    "No addresses found for station: " + station,
                    HttpStatus.NOT_FOUND);
        }
        return addresses;
    }
    public FireDTO getResidentsByAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new AlertApiException("Address cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        // Récupérer le numéro de la station
        String stationNumber = fireStationRepository.findByAddress(address)
                .map(FireStation::getStation)
                .orElseThrow(() -> new AlertApiException(
                        "No fire station found for address: " + address,
                        HttpStatus.NOT_FOUND));

        // Obtenir la liste des personnes à cette adresse avec leurs informations
        List<PersonInfoDTO> residents = personService.getAllPersons().stream()
                .filter(person -> address.equalsIgnoreCase(person.getAddress()))
                .map(person -> personService.getPersonInfo(person.getFirstName(), person.getLastName()).get(0))
                .collect(Collectors.toList());

        if (residents.isEmpty()) {
            throw new AlertApiException(
                    "No residents found at address: " + address,
                    HttpStatus.NOT_FOUND);
        }

        return FireDTO.builder()
                .stationNumber(stationNumber)
                .residents(residents)
                .build();
    }

}