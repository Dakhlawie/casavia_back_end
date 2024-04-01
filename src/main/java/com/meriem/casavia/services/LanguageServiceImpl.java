package com.meriem.casavia.services;

import com.meriem.casavia.entities.language;
import com.meriem.casavia.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    LanguageRepository languageserv;
    @Override
    public language ajouter_language(language language) {
        return languageserv.save(language);
    }
}
