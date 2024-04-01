package com.meriem.casavia.services;

import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository UserRep;
    @Override
    public User ajouterUser(User c) {
      return UserRep.save(c);
    }

    @Override
    public User modifierUser(User c) {
        return UserRep.save(c);
    }

    @Override
    public User getUser(Long id) {
        return UserRep.findById(id).get();
    }
}
