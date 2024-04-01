package com.meriem.casavia.services;

import com.meriem.casavia.entities.Equipement;

import java.util.List;

public interface EquipementService {
    Equipement createEquipement(Equipement equipement);


    void deleteEquipement(Long id);

    List<Equipement> getAllEquipements();

}
