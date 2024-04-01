package com.meriem.casavia.services;

import com.meriem.casavia.entities.User;

public interface UserService {
    User ajouterUser(User c);
    User modifierUser(User c);
    User getUser(Long id);
}
