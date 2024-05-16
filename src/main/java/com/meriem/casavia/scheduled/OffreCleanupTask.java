package com.meriem.casavia.scheduled;

import com.meriem.casavia.entities.OffreHebergement;

import com.meriem.casavia.repositories.OffreRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class OffreCleanupTask {

    private final OffreRepository offreHebergementRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public OffreCleanupTask(OffreRepository offreHebergementRepository) {
        this.offreHebergementRepository = offreHebergementRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanUpExpiredOffres() {
        List<OffreHebergement> offres = offreHebergementRepository.findAll();
        Date currentDate = new Date();
        for (OffreHebergement offre : offres) {
            try {
                Date endDate = dateFormat.parse(offre.getEnd_date());
                if (endDate.before(currentDate)) {
                    offreHebergementRepository.delete(offre);
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        }
    }
}
