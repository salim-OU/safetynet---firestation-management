package com.safetynet.service;

import com.safetynet.dto.ChildAlertDTO;
import com.safetynet.dto.PersonDTO;
import com.safetynet.dto.PersonInfoDTO;
import com.safetynet.exception.AlertApiException;
import com.safetynet.model.FireStation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final DataRepository dataRepository;

    // Méthode pour obtenir toutes les personnes
    public List<Person> getAllPersons() {
        return dataRepository.findAllPersons();
    }

    // Trouver une personne par son nom
    public Person findByName(String firstName, String lastName) {
        return dataRepository.findAllPersons().stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    // Récupérer les personnes d'une ville avec leurs informations détaillées
    public List<PersonInfoDTO> getEmailsByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new AlertApiException("City cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        List<PersonInfoDTO> personInfos = dataRepository.findAllPersons().stream()
                .filter(person -> city.equalsIgnoreCase(person.getCity()))
                .map(this::convertToPersonInfoDTO)
                .collect(Collectors.toList());

        if (personInfos.isEmpty()) {
            throw new AlertApiException(
                    "No persons found for city: " + city,
                    HttpStatus.NOT_FOUND
            );
        }

        return personInfos;
    }

    // Obtenir les informations détaillées d'une personne
    public List<PersonInfoDTO> getPersonInfo(String firstName, String lastName) {
        List<Person> persons = dataRepository.findAllPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if (persons.isEmpty()) {
            throw new AlertApiException(
                    "No person found with name: " + firstName + " " + lastName,
                    HttpStatus.NOT_FOUND);
        }

        return persons.stream()
                .map(this::convertToPersonInfoDTO)
                .collect(Collectors.toList());
    }

    // Convertir une Person en PersonInfoDTO
    private PersonInfoDTO convertToPersonInfoDTO(Person person) {
        MedicalRecord medicalRecord = findMedicalRecord(person.getFirstName(), person.getLastName());

        return PersonInfoDTO.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress())
                .email(person.getEmail())
                .age(calculateAge(medicalRecord.getBirthdate()))
                .medications(medicalRecord.getMedications())
                .allergies(medicalRecord.getAllergies())
                .build();
    }

    // Trouver le dossier médical d'une personne
    private MedicalRecord findMedicalRecord(String firstName, String lastName) {
        return dataRepository.findAllMedicalRecords().stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(firstName)
                        && mr.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(new MedicalRecord()); // Retourne un record vide si non trouvé
    }

    // Calculer l'âge à partir de la date de naissance
    private Integer calculateAge(String birthdate) {
        if (birthdate == null || birthdate.trim().isEmpty()) {
            return null;
        }

        try {
            LocalDate birthDate = LocalDate.parse(birthdate,
                    DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (Exception e) {
            return null;
        }
    }
    // Obtenir les enfants et membres du foyer pour une adresse donnée
    public List<ChildAlertDTO> getChildrenByAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new AlertApiException("Address cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        // Récupérer toutes les personnes à cette adresse
        List<Person> residentsAtAddress = dataRepository.findAllPersons().stream()
                .filter(p -> address.equalsIgnoreCase(p.getAddress()))
                .collect(Collectors.toList());

        if (residentsAtAddress.isEmpty()) {
            throw new AlertApiException(
                    "No residents found at address: " + address,
                    HttpStatus.NOT_FOUND
            );
        }

        // Filtrer pour ne garder que les enfants et créer leurs DTOs
        List<ChildAlertDTO> children = residentsAtAddress.stream()
                .filter(person -> {
                    Integer age = calculateAge(findMedicalRecord(person.getFirstName(),
                            person.getLastName()).getBirthdate());
                    return age != null && age <= 18;
                })
                .map(child -> createChildAlert(child, residentsAtAddress))
                .collect(Collectors.toList());

        return children;
    }

    // Créer un ChildAlertDTO pour un enfant avec les membres de son foyer
    private ChildAlertDTO createChildAlert(Person child, List<Person> householdMembers) {
        Integer age = calculateAge(findMedicalRecord(child.getFirstName(),
                child.getLastName()).getBirthdate());

        List<PersonDTO> otherMembers = householdMembers.stream()
                .filter(p -> !p.equals(child))
                .map(p -> PersonDTO.builder()
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .build())
                .collect(Collectors.toList());

        return ChildAlertDTO.builder()
                .firstName(child.getFirstName())
                .lastName(child.getLastName())
                .age(age != null ? age : 0)
                .householdMembers(otherMembers)
                .build();
    }
    public List<String> getPhoneNumbersByStation(String stationNumber) {
        if (stationNumber == null || stationNumber.trim().isEmpty()) {
            throw new AlertApiException("Station number cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        // Récupérer toutes les adresses couvertes par la station
        List<String> stationAddresses = dataRepository.findAllFireStations().stream()
                .filter(station -> stationNumber.equals(station.getStation()))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        if (stationAddresses.isEmpty()) {
            throw new AlertApiException(
                    "No addresses found for station number: " + stationNumber,
                    HttpStatus.NOT_FOUND
            );
        }

        // Récupérer tous les numéros de téléphone des résidents à ces adresses
        List<String> phoneNumbers = dataRepository.findAllPersons().stream()
                .filter(person -> stationAddresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct() // Éliminer les doublons
                .collect(Collectors.toList());

        if (phoneNumbers.isEmpty()) {
            throw new AlertApiException(
                    "No phone numbers found for station: " + stationNumber,
                    HttpStatus.NOT_FOUND
            );
        }

        return phoneNumbers;
    }

}