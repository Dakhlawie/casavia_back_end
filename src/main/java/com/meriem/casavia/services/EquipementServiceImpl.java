package com.meriem.casavia.services;

import com.meriem.casavia.entities.Equipement;
import com.meriem.casavia.repositories.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EquipementServiceImpl implements EquipementService{
    @Autowired
    private EquipementRepository equipementRepository;
    @Override
    public Equipement createEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public void deleteEquipement(Long id) {
        equipementRepository.deleteById(id);
    }

    @Override
    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }
}
