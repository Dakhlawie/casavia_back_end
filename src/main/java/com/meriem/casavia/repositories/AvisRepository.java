package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AvisRepository extends JpaRepository<Avis,Long> {
    List<Avis> findByHebergement(Hebergement hebergement, Sort sort);
    @Query("SELECT new map(avg(a.staff) as staffAverage, avg(a.location) as locationAverage, avg(a.comfort) as comfortAverage, avg(a.facilities) as facilitiesAverage, avg(a.cleanliness) as cleanlinessAverage, (avg(a.staff) + avg(a.location) + avg(a.comfort) + avg(a.facilities) + avg(a.cleanliness)) / 5 as overallAverage) FROM Avis a WHERE a.hebergement = :hebergement")
    Map<String, Double> findAveragesByHebergement(@Param("hebergement") Hebergement hebergement);
    @Query("SELECT COUNT(a) FROM Avis a WHERE a.hebergement = :hebergement AND a.avis IS NOT NULL AND a.avis != ''")
    long countPositiveReviews(Hebergement hebergement);

    @Query("SELECT COUNT(a) FROM Avis a WHERE a.hebergement = :hebergement AND a.avisNegative IS NOT NULL AND a.avisNegative != ''")
    long countNegativeReviews(Hebergement hebergement);
    @Query(value = "SELECT EXTRACT(MONTH FROM STR_TO_DATE(a.date, '%d/%m/%Y')) AS month, " +
            "SUM(CASE WHEN a.avis IS NOT NULL AND a.avis != '' THEN 1 ELSE 0 END) AS positiveCount, " +
            "SUM(CASE WHEN a.avis_negative IS NOT NULL AND a.avis_negative != '' THEN 1 ELSE 0 END) AS negativeCount " +
            "FROM Avis a " +
            "WHERE a.hebergement = :hebergement AND " +
            "EXTRACT(YEAR FROM STR_TO_DATE(a.date, '%d/%m/%Y')) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY EXTRACT(MONTH FROM STR_TO_DATE(a.date, '%d/%m/%Y')) " +
            "ORDER BY month", nativeQuery = true)
    List<Object[]> findMonthlyPositiveAndNegativeCounts(Hebergement hebergement);
    @Query("SELECT FUNCTION('MONTH', a.date) AS month, " +
            "SUM(CASE WHEN a.avis IS NOT NULL AND a.avis != '' THEN 1 ELSE 0 END) AS positiveCount " +
            "FROM Avis a " +
            "WHERE a.hebergement = :hebergement AND " +
            "FUNCTION('YEAR', a.date) = FUNCTION('YEAR', CURRENT_DATE) " +
            "GROUP BY FUNCTION('MONTH', a.date) " +
            "ORDER BY FUNCTION('MONTH', a.date)")
    List<Object[]> findMonthlyPositiveCounts(@Param("hebergement") Hebergement hebergement);

    @Query("SELECT FUNCTION('MONTH', a.date) AS month, " +
            "SUM(CASE WHEN a.avisNegative IS NOT NULL AND a.avisNegative != '' THEN 1 ELSE 0 END) AS negativeCount " +
            "FROM Avis a " +
            "WHERE a.hebergement = :hebergement AND " +
            "FUNCTION('YEAR', a.date) = FUNCTION('YEAR', CURRENT_DATE) " +
            "GROUP BY FUNCTION('MONTH', a.date) " +
            "ORDER BY FUNCTION('MONTH', a.date)")
    List<Object[]> findMonthlyNegativeCounts(@Param("hebergement") Hebergement hebergement);



}
