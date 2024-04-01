package com.meriem.casavia.services;

import com.meriem.casavia.entities.Position;
import com.meriem.casavia.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService{
    @Autowired
    PositionRepository positionRep;

    @Override
    public Position ajouterPosition(Position p) {
        return positionRep.save(p);
    }
}
