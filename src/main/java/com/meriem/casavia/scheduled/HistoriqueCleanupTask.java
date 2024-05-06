package com.meriem.casavia.scheduled;

import com.meriem.casavia.entities.Historique;
import com.meriem.casavia.repositories.HistoriqueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.transaction.Transactional;

@Component
public class HistoriqueCleanupTask {

    private final HistoriqueRepository historiqueRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public HistoriqueCleanupTask(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanUpOldHistoriques() {
        List<Historique> historiques = historiqueRepository.findAll();
        Date currentDate = new Date();
        for (Historique historique : historiques) {
            try {
                Date checkInDate = dateFormat.parse(historique.getCheck_in());
                if (checkInDate.before(currentDate)) {
                    historiqueRepository.delete(historique);
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        }
    }
}
