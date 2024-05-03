package com.meriem.casavia.services;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Dates;

import java.util.List;

public interface DatesService {
    Dates ajouterDates(Dates d);
    void deleteDates(long id);
    List<Dates> getAllDates();
    boolean isRoomAvailable(long id , String checkIn, String checkOut);
    boolean isHebergementAvailable(long id,String checkIn, String checkOut);
}
