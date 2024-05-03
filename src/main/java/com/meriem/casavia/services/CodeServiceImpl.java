package com.meriem.casavia.services;

import com.meriem.casavia.entities.Code;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CodeServiceImpl implements CodeService{

    private CodeRepository codeRep;
    @Override
    public Code getCodeById(Long id) {
        // TODO Auto-generated method stub
        return codeRep.findById(id).get();
    }

    @Override
    public Code getByCode(String code) {
        // TODO Auto-generated method stub
        return codeRep.findByCode(code);
    }

    @Override
    public void deleteCode(Long id ) {
        // TODO Auto-generated method stub
        codeRep.deleteById(id);

    }
    @Override
    public Code getCodeByUser(User user) {
        return codeRep.findByUser(user);
    }

    @Override
    public Code addCode(Code code) {
        // TODO Auto-generated method stub
        return codeRep.save(code);
    }
    @Override
    public void deleteCode(Code code) {
        codeRep.delete(code);
    }


}