package com.meriem.casavia.services;

import com.meriem.casavia.entities.*;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.LikeRepository;
import com.meriem.casavia.repositories.RecommandationRepository;
import com.meriem.casavia.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HebergementServiceImpl implements HebergementService{

    @Autowired
    private HebergementRepository hebergementRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RecommandationRepository recommandationRepository;


    @Override
    public Hebergement createHebergement(Hebergement hebergement) {
        return hebergementRepository.save(hebergement);
    }
    @Override
    public Hebergement updateHebergement(long id, Hebergement hebergement) {

        Hebergement h = hebergementRepository.findById(id).get();
        h.setNom(hebergement.getNom());
        h.setDescription(hebergement.getDescription());
       h.setVille(hebergement.getVille());
       h.setPays(hebergement.getPays());
        h.setPrix(hebergement.getPrix());
        h.setDistance(hebergement.getDistance());
        h.setPhone(hebergement.getPhone());
       h.setEmail(hebergement.getEmail());
        h.setAdresse(hebergement.getAdresse());
        h.setPolitiqueAnnulation(hebergement.getPolitiqueAnnulation());
        h.setNbEtoile(hebergement.getNbEtoile());
        h.setNb_Salles_De_Bains(hebergement.getNb_Salles_De_Bains());
       h.setNb_Chambres(hebergement.getNb_Chambres());
        h.setWebsite(hebergement.getWebsite());
        h.setFacebook(hebergement.getFacebook());
        h.setInstagram(hebergement.getInstagram());
        h.setFax(hebergement.getFax());
        
        h.setLanguages(hebergement.getLanguages());
        h.setCountry_code(hebergement.getCountry_code());
        h.setCurrency(hebergement.getCurrency());
        h.setCancellationfees(hebergement.getCancellationfees());



            return hebergementRepository.save(h);

    }



    @Override
    public void deleteHebergement(Long id) {
        hebergementRepository.deleteById(id);
    }



    @Override
    public List<Hebergement> getAllHebergements() {
        return hebergementRepository.findAll();
    }
    @Override
    public List<Hebergement> findByCategorie(Categorie categorie) {
        return hebergementRepository.findByCategorie(categorie);
    }
    @Override
    public List<Hebergement> findByCategorieIdCat(Long id) {
        return hebergementRepository.findByCategorieIdCat(id);
    }

    @Override
    public List<Hebergement> findHebergementsByPerson(Long id) {
        return hebergementRepository.findHebergementsByPersonId(id);
    }

    @Override
    public Hebergement getHebergementByNom(String nom) {
        return hebergementRepository.getHebergementsByNom(nom);
    }

    @Override
    public Long gethebergement_idByNom(String nom) {
        return hebergementRepository.getIdByNom(nom);
    }

    @Override
    public Hebergement getHebergementById(Long id) {
        return hebergementRepository.findById(id).get();
    }
    @Override
    public List<Hebergement> findHebergementsLikedByUser(User user) {
        List<Like> likes = likeRepository.findByUser(user);
        return likes.stream().map(Like::getHebergement).collect(Collectors.toList());
    }
@Override
    public List<Hebergement> findHebergementsReservedByUser(User user) {
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .map(Reservation::getHebergement)
                .distinct()
                .collect(Collectors.toList());
    }
    public Hebergement getHebergementFromRecommandation(Long recommandationId) {
        Optional<Recommandation> recommandation = recommandationRepository.findById(recommandationId);
        if (recommandation.isPresent()) {
            return recommandation.get().getHebergement();
        } else {
            throw new RuntimeException("Recommandation not found with id " + recommandationId);
        }
    }
    @Override
    public List<Hebergement> findNearbyHebergements(double latitude, double longitude) {
        List<Hebergement> allHebergements = hebergementRepository.findAll();
        return allHebergements.stream()
                .filter(hebergement -> hebergement.getPositions().stream()
                        .anyMatch(position -> {
                            double hebergementLatitude = position.getLatitude();
                            double hebergementLongitude = position.getLongitude();
                            double distance = calculateDistance(latitude, longitude, hebergementLatitude, hebergementLongitude);
                            return distance <= 10;
                        }))
                .collect(Collectors.toList());
    }
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
