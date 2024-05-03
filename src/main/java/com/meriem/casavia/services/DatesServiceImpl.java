package com.meriem.casavia.services;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Dates;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.repositories.ChambreRepository;
import com.meriem.casavia.repositories.DatesRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class DatesServiceImpl implements DatesService{
    @Autowired
    DatesRepository DatesRep;
    @Autowired
    ChambreRepository chambreRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Override
    public Dates ajouterDates(Dates d) {
        return this.DatesRep.save(d);
    }

    @Override
    public void deleteDates(long id) {
        this.DatesRep.deleteById(id);
    }

    @Override
    public List<Dates> getAllDates() {
        return this.DatesRep.findAll();
    }

    @Override
    public boolean isRoomAvailable(long id, String checkInStr, String checkOutStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkIn = LocalDate.parse(checkInStr, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutStr, formatter);
Chambre chambre=chambreRep.findById(id).get();
        System.out.println(chambre);
        List<Dates> reservations = this.DatesRep.findByChambre(chambre);
        System.out.println("*************************");
        System.out.println(reservations);
        if (reservations.isEmpty()) {
            System.out.println("hello");
            System.out.println(chambre.getNbRoom() > 0);
            return chambre.getNbRoom() > 0;
        }
        int totalRoomsBooked = 0;

        for (Dates reservation : reservations) {

            LocalDate startDate = LocalDate.parse(reservation.getStartDate(),formatter);
            LocalDate endDate = LocalDate.parse(reservation.getEndDate(),formatter);


            if (!(checkOut.isBefore(startDate) || checkIn.isAfter(endDate))) {
                totalRoomsBooked += 1;
            }
        }


        return totalRoomsBooked < chambre.getNbRoom();
    }

    @Override
    public boolean isHebergementAvailable(long id, String checkInStr, String checkOutStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkIn = LocalDate.parse(checkInStr, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutStr, formatter);
        Hebergement hebergement=hebergementRep.findById(id).get();
        List<Dates> reservations = this.DatesRep.findByHebergement(hebergement);
        for (Dates reservation : reservations) {
            LocalDate startDate = LocalDate.parse(reservation.getStartDate(),formatter);
            LocalDate endDate = LocalDate.parse(reservation.getEndDate(),formatter);


            if (!(checkOut.isBefore(startDate) || checkIn.isAfter(endDate))) {
                return false;
            }
        }
        return true;
    }
}


