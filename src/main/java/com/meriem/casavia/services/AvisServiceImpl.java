package com.meriem.casavia.services;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.repositories.AvisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AvisServiceImpl implements AvisService{
    @Autowired
    AvisRepository avisRepo;
    @Override
    public Avis ajouterAvis(Avis a) {
        return avisRepo.save(a);
    }

    @Override
    public Avis modifierAvis(Avis a) {
        return avisRepo.save(a);
    }

    @Override
    public void supprimerAvis(Long id) {
        avisRepo.deleteById(id);
    }

    @Override
    public List<Avis> getAllAvis() {
        return avisRepo.findAll();
    }
}
