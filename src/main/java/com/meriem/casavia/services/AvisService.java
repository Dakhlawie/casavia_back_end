package com.meriem.casavia.services;

import com.meriem.casavia.entities.Avis;

import java.util.List;

public interface AvisService {
    Avis ajouterAvis(Avis a);
    Avis modifierAvis(Avis a);
    void supprimerAvis(Long id);
    List<Avis> getAllAvis();

}
