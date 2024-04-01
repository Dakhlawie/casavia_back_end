package com.meriem.casavia.services;

import com.meriem.casavia.entities.Chambre;

import java.util.List;

public interface ChambreService {
    Chambre ajouterChambre(Chambre c);
    List<Chambre> getAllChambre();
    Chambre modifierChambre(long id,Chambre c);
    Chambre getChambreById(Long id);
}
