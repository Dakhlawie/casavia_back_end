package com.meriem.casavia.services;

import com.meriem.casavia.entities.Person;
import com.meriem.casavia.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonRepository PartenaireRep;
    @Override
    public Person ajouterPerson(Person p) {
        return PartenaireRep.save(p);
    }

    public Person modifierPerson(Long id, Person p) {
        // Vérifie si la personne existe dans la base de données
        Person person = PartenaireRep.findById(id).get();
        person.setNom(p.getNom());
        person.setPrenom(p.getPrenom());
        person.setEmail(p.getEmail());
        person.setTelephone(p.getTelephone());


            return PartenaireRep.save(person);

    }

    @Override
    public void supprimerPerson(Long id) {
        PartenaireRep.deleteById(id);

    }

    @Override
    public List<Person> getAllPerson() {
        return PartenaireRep.findByRole("partenaire");
    }

    @Override
    public Person getPersonById(Long id) {
        return PartenaireRep.findById(id).get();



}}
