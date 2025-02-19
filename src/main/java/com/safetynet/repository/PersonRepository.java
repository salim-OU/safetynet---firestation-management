package com.safetynet.repository;

import com.safetynet.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final List<Person> persons = new ArrayList<>();
    public List<Person> findAll() {
        return persons;
    }
    public void save(Person person) {
        persons.add(person);
    }
    public void delete(Person person) {
        persons.remove(person);
    }

    public void clear() {
    }

    public void addAll(List persons) {

    }
}
