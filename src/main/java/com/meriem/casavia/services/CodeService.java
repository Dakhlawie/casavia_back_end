package com.meriem.casavia.services;

import com.meriem.casavia.entities.Code;
import com.meriem.casavia.entities.User;

public interface CodeService {
    Code getCodeById(Long id);
    Code getByCode(String code );
    void deleteCode(Long id);
    Code addCode(Code code);

    Code getCodeByUser(User user);
    void deleteCode(Code code);
}

