package com.meriem.casavia.scheduled;

import com.meriem.casavia.entities.Historique;
import com.meriem.casavia.repositories.HistoriqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class HistoriqueCleanupTask {

    private final HistoriqueRepository historiqueRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public HistoriqueCleanupTask(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateHistoriqueStates() {
        List<Historique> historiques = historiqueRepository.findAll();
        Date currentDate = new Date();
        for (Historique historique : historiques) {
            try {
                Date checkOutDate = dateFormat.parse(historique.getCheck_out());
                if (checkOutDate.before(currentDate) && "active".equals(historique.getEtat())) {
                    historique.setEtat("expired");
                    historiqueRepository.save(historique);
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        }
    }
}
