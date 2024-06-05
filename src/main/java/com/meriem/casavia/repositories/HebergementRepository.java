package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface HebergementRepository extends JpaRepository<Hebergement,Long> {
    List<Hebergement> findByCategorie (Categorie categorie);
    List<Hebergement> findByCategorieIdCat(Long id);
    Hebergement getHebergementsByNom(String nom);
    Long getIdByNom(String nom);
    @Query("SELECT h FROM Hebergement h WHERE h.person.person_id = :personId")
    List<Hebergement> findHebergementsByPersonId(Long personId);
    List<Hebergement> findByVille(String ville);
    List<Hebergement> findByVilleAndCategorie(String ville,Categorie c);
    @Query("SELECT h.categorie FROM Hebergement h WHERE h.hebergement_id = :id")
    Categorie findCategorieByHebergementId(@Param("id") Long id);
    @Query("SELECT h FROM Hebergement h WHERE (h.ville LIKE %:query% OR h.pays LIKE %:query%) AND h.categorie = :categorie")
    List<Hebergement> findByVilleOrPaysAndCategorie(@Param("query") String query, @Param("categorie") Categorie categorie);

    long countBy();
    @Query("SELECT DISTINCT c FROM Hebergement h JOIN h.equipements e JOIN e.categorie c WHERE h.hebergement_id = :hebergementId")
    List<CategorieEquipement> findCategoriesOfEquipementsByHebergementId(@Param("hebergementId") Long hebergementId);
    @Query("SELECT h.languages FROM Hebergement h WHERE h.hebergement_id = :hebergementId")
    List<language> findLanguagesByHebergementId(@Param("hebergementId") Long hebergementId);

    @Query("SELECT COUNT(i) > 0 FROM Image i WHERE i.hebergement.hebergement_id = :hebergementId")
    boolean hasImages(Long hebergementId);


    @Query("SELECT COUNT(e) > 0 FROM Equipement e JOIN e.hebergements h WHERE h.hebergement_id = :hebergementId")
    boolean hasEquipments(Long hebergementId);


    @Query("SELECT COUNT(p) > 0 FROM Position p WHERE p.hebergement.hebergement_id = :hebergementId")
    boolean hasLocation(Long hebergementId);


    @Query("SELECT COUNT(c) > 0 FROM Chambre c WHERE c.hebergement.hebergement_id = :hebergementId")
    boolean hasRooms(Long hebergementId);
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements AND h.politiqueAnnulation = 'Free Cancellation'")
    List<Hebergement> findHebergementsWithFreeCancellation(@Param("hebergements") List<Hebergement> hebergements);
    @Query("SELECT h FROM Hebergement h JOIN Avis a ON h = a.hebergement WHERE h IN :hebergements GROUP BY h HAVING COUNT(a) > 0 AND (AVG(a.staff) + AVG(a.location) + AVG(a.comfort) + AVG(a.facilities) + AVG(a.cleanliness)) / 5 BETWEEN :overallAverage AND :upperBound AND COUNT(a) > 0")
    List<Hebergement> findHebergementsByOverallAverage(
            @Param("hebergements") List<Hebergement> hebergements,
            @Param("overallAverage") Double overallAverage,
            @Param("upperBound") Double upperBound
    );

    @Query("SELECT h FROM Hebergement h JOIN Avis a ON h = a.hebergement WHERE h IN :hebergements GROUP BY h HAVING COUNT(a) > 0 AND (AVG(a.security) + AVG(a.location) + AVG(a.comfort) + AVG(a.facilities) + AVG(a.cleanliness)) / 5 BETWEEN :overallAverage AND :upperBound AND COUNT(a) > 0")
    List<Hebergement> findOtherHebergementsByOverallAverage(
            @Param("hebergements") List<Hebergement> hebergements,
            @Param("overallAverage") Double overallAverage,
            @Param("upperBound") Double upperBound
    );
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements AND h.nbEtoile = :nbEtoiles")
    List<Hebergement> findHebergementsByNbEtoiles(
            @Param("hebergements") List<Hebergement> hebergements,
            @Param("nbEtoiles") int nbEtoiles
    );
    @Query("SELECT DISTINCT h FROM Hebergement h JOIN h.equipements e JOIN e.categorie c WHERE h IN :hebergements AND c.nom = 'work' AND SIZE(h.equipements) > 0")
    List<Hebergement> findHebergementsWithWorkEquipements(@Param("hebergements") List<Hebergement> hebergements);

    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements AND h.prix BETWEEN :minPrice AND :maxPrice")
    List<Hebergement> findHebergementsByPriceRange(
            @Param("hebergements") List<Hebergement> hebergements,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements AND (SELECT MIN(c.prix) FROM Chambre c WHERE c.hebergement = h) BETWEEN :minPrice AND :maxPrice")
    List<Hebergement> findHebergementsByChambrePriceRange(
            @Param("hebergements") List<Hebergement> hebergements,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );
    @Query("SELECT MIN(c.prix) FROM Chambre c WHERE c.hebergement = :hebergement")
    Double findMinChambrePriceByHebergement(@Param("hebergement") Hebergement hebergement);
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements AND h.nb_Chambres = :nbChambres")
    List<Hebergement> findHebergementsByNbChambres(
            @Param("hebergements") List<Hebergement> hebergements,
            @Param("nbChambres") int nbChambres
    );
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements ORDER BY h.nbEtoile ASC")
    List<Hebergement> sortHebergementsByNbEtoilesAsc(@Param("hebergements") List<Hebergement> hebergements);

    // Méthode pour trier les hébergements par nombre d'étoiles (descendant)
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements ORDER BY h.nbEtoile DESC")
    List<Hebergement> sortHebergementsByNbEtoilesDesc(@Param("hebergements") List<Hebergement> hebergements);

    // Méthode pour trier les hébergements par moyenne globale (ascendant)
    @Query("SELECT h FROM Hebergement h JOIN Avis a ON h = a.hebergement WHERE h IN :hebergements GROUP BY h ORDER BY (COALESCE(AVG(a.staff), 0) + COALESCE(AVG(a.location), 0) + COALESCE(AVG(a.comfort), 0) + COALESCE(AVG(a.facilities), 0) + COALESCE(AVG(a.cleanliness), 0)) / 5 ASC")
    List<Hebergement> sortHebergementsByOverallAverageAsc(@Param("hebergements") List<Hebergement> hebergements);

    // Méthode pour trier les hébergements par moyenne globale (descendant)
    @Query("SELECT h FROM Hebergement h LEFT JOIN Avis a ON h = a.hebergement WHERE h IN :hebergements GROUP BY h ORDER BY (COALESCE(AVG(a.staff), 0) + COALESCE(AVG(a.location), 0) + COALESCE(AVG(a.comfort), 0) + COALESCE(AVG(a.facilities), 0) + COALESCE(AVG(a.cleanliness), 0)) / 5 DESC")
    List<Hebergement> sortHebergementsByOverallAverageDesc(@Param("hebergements") List<Hebergement> hebergements);

    // Méthode pour trier les hébergements other que hotel  par moyenne globale (ascendant)
    @Query("SELECT h FROM Hebergement h JOIN Avis a ON h = a.hebergement WHERE h IN :hebergements GROUP BY h ORDER BY (COALESCE(AVG(a.security), 0) + COALESCE(AVG(a.location), 0) + COALESCE(AVG(a.comfort), 0) + COALESCE(AVG(a.facilities), 0) + COALESCE(AVG(a.cleanliness), 0)) / 5 ASC")
    List<Hebergement> sortOtherHebergementsByOverallAverageAsc(@Param("hebergements") List<Hebergement> hebergements);

    // Méthode pour trier les hébergements  other que hotel par moyenne globale (descendant)
    @Query("SELECT h FROM Hebergement h LEFT JOIN Avis a ON h = a.hebergement WHERE h IN :hebergements GROUP BY h ORDER BY (COALESCE(AVG(a.security), 0) + COALESCE(AVG(a.location), 0) + COALESCE(AVG(a.comfort), 0) + COALESCE(AVG(a.facilities), 0) + COALESCE(AVG(a.cleanliness), 0)) / 5 DESC")
    List<Hebergement> sortOtherHebergementsByOverallAverageDesc(@Param("hebergements") List<Hebergement> hebergements);
    // Méthode pour trier les hébergements par prix (ascendant)
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements ORDER BY h.prix ASC")
    List<Hebergement> sortHebergementsByPrixAsc(@Param("hebergements") List<Hebergement> hebergements);
    @Query("SELECT h FROM Hebergement h WHERE h IN :hebergements ORDER BY (SELECT MIN(c.prix) FROM Chambre c WHERE c.hebergement = h) ASC")
    List<Hebergement> sortHebergementsByMinChambrePriceAsc(@Param("hebergements") List<Hebergement> hebergements);
    @Query("SELECT AVG((a.staff + a.location + a.comfort + a.facilities + a.cleanliness) / 5.0) FROM Avis a WHERE a.hebergement = :hebergement")
    Double findOverallAverageByHebergement(@Param("hebergement") Hebergement hebergement);
    @Query("SELECT AVG((a.security + a.location + a.comfort + a.facilities + a.cleanliness) / 5.0) FROM Avis a WHERE a.hebergement = :hebergement")
    Double findOverallAverageByOtherHebergement(@Param("hebergement") Hebergement hebergement);
    @Query("SELECT h FROM Hebergement h " +
            "JOIN h.avisList a " +
            "JOIN h.reservations r " +
            "GROUP BY h " +
            "HAVING (SUM(a.moyenne) / COUNT(a) BETWEEN 4 AND 5) " +
            "AND (COUNT(a.avis) > COUNT(a.avisNegative)) " +
            "ORDER BY COUNT(r) DESC, COUNT(a.avis) DESC")
    List<Hebergement> findTopByAvisPositiveAndReservations(Pageable pageable);
    @Query("SELECT DISTINCT h.ville FROM Hebergement h JOIN h.likes l WHERE l.user.user_id = :userId")
    List<String> findLikedHebergementCitiesByUser(@Param("userId") Long userId);
    @Query("SELECT DISTINCT h.ville FROM Hebergement h JOIN h.reservations r WHERE r.user.user_id = :userId")
    List<String> findReservedHebergementCitiesByUser(@Param("userId") Long userId);
    @Query("SELECT DISTINCT h.ville FROM Hebergement h JOIN Historique hist ON h.ville = hist.lieu WHERE hist.user.user_id = :userId")
    List<String> findSearchedHebergementCitiesByUser(@Param("userId") Long userId);
    @Query("SELECT h FROM Hebergement h JOIN h.offres o WHERE h.ville IN :cities ")
    List<Hebergement> findHebergementsWithOffersByCities(@Param("cities") List<String> cities);
    @Query("SELECT h FROM Hebergement h " +
            "JOIN h.reservations r " +
            "WHERE r.user = :user " +
            "AND r.etat = 'COMPLETED' " +
            "AND NOT EXISTS (SELECT a FROM Avis a WHERE a.hebergement = h AND a.user = :user)")
    List<Hebergement> findReservedHebergementsWithoutAvisByUser(@Param("user") User user);
    @Query("SELECT o FROM OffreHebergement o WHERE o.hebergement.hebergement_id = :hebergementId")
    List<OffreHebergement> findOffersByHebergementId(@Param("hebergementId") Long hebergementId);



}





