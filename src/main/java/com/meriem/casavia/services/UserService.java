package com.meriem.casavia.services;

import com.meriem.casavia.entities.User;

public interface UserService {
    User ajouterUser(User c);
    User modifierUserNom(long id,String nom);
    User modifierUserPrenom(long id,String prenom);
    User modifierUserEmail(long id,String email);
    User modifierUserPays(long id,String pays);
    User modifierUsertlf(long id,String phone);

    User getUser(Long id);
    String sendCodeByEmail(String email,String code);
    boolean ChangePassword(long id,String currentpassword,String newpassword);
    String sendResetPasswordEmail(String email, String firstName, String lastName);
    User RecoverPassword(long id,String newpassword);


}
