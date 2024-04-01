package com.meriem.casavia.services;

import com.meriem.casavia.entities.Categorie;

import java.util.List;

public interface CategorieService {
    Categorie ajouterCategorie(Categorie c);
    List<Categorie> getAllCategorie();
}
