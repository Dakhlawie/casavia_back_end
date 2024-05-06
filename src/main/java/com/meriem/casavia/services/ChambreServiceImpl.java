package com.meriem.casavia.services;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.repositories.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChambreServiceImpl implements ChambreService{
    @Autowired
    ChambreRepository chambreRep;
    @Override
    public Chambre ajouterChambre(Chambre c) {
        return chambreRep.save(c);
    }

    @Override
    public List<Chambre> getAllChambre() {
        return chambreRep.findAll();
    }

    @Override
    public Chambre modifierChambre(long id,Chambre c) {
        Chambre ch=chambreRep.findById(id).get();

        ch.setDescription(c.getDescription());
        ch.setPrix(c.getPrix());
        ch.setEquipements(c.getEquipements());
        ch.setNbRoom(c.getNbRoom());
        return chambreRep.save(ch);

    }

    @Override
    public Chambre getChambreById(Long id) {
        return chambreRep.findById(id).get();
    }
}
