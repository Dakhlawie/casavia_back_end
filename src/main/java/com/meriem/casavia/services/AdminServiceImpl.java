package com.meriem.casavia.services;

import com.meriem.casavia.entities.Admin;
import com.meriem.casavia.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminRepository AdminRep;

    @Override
    public Admin ajouterAdmin(Admin a) {
        return AdminRep.save(a);
    }
}
