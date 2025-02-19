package com.safetynet.controller;


import com.safetynet.dto.ChildAlertDTO;
import com.safetynet.dto.PersonInfoDTO;
import com.safetynet.model.Person;
import com.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/person")
    public Person getPerson(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.findByName(firstName, lastName);
    }
    @GetMapping("/communityEmail")
    public ResponseEntity<List<PersonInfoDTO>> getCommunityEmails(@RequestParam String city) {
        return ResponseEntity.ok(personService.getEmailsByCity(city));
    }

    // Vous pouvez réutiliser PersonInfoDTO pour d'autres endpoints
    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfo(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        // Implémentation à venir
        return null;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDTO>> getChildAlert(@RequestParam String address) {
        List<ChildAlertDTO> children = personService.getChildrenByAddress(address);

        if (children.isEmpty()) {
            return ResponseEntity.ok().build();  // Retourne une réponse vide s'il n'y a pas d'enfants
        }

        return ResponseEntity.ok(children);
    }
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam String firestation) {
        List<String> phoneNumbers = personService.getPhoneNumbersByStation(firestation);
        return ResponseEntity.ok(phoneNumbers);
    }

}
