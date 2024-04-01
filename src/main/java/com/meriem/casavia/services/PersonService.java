package com.meriem.casavia.services;

import com.meriem.casavia.entities.Person;

import java.util.List;

public interface PersonService {
    Person ajouterPerson(Person p);
    Person modifierPerson(Long id, Person p);
    void supprimerPerson(Long id);
    List<Person> getAllPerson();
    Person getPersonById(Long id );


}
