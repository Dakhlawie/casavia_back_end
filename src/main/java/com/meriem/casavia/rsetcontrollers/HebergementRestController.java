package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.PriceRangeRequest;
import com.meriem.casavia.entities.*;

import com.meriem.casavia.repositories.*;

import com.meriem.casavia.services.CurrencyConversionService;
import com.meriem.casavia.services.HebergementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RequestMapping("/hebergement")
@RestController
@CrossOrigin
public class HebergementRestController {
    @Autowired
    private HebergementService hebergementService;
    @Autowired
    CategorieRepository categorieRep;
    @Autowired
    PersonRepository  personRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    VideoRepository videoRep;
    @Autowired
    UserRepository userRep;
    @Autowired
    private CurrencyConversionService currencyConversionService;

    @GetMapping("/all")
    public List<Hebergement> getAllHebergements() {
        return hebergementService.getAllHebergements();
    }
    @PutMapping("/{id}/updateMoyenne")
    public void updateHebergementMoyenne(@PathVariable Long id,
                                         @RequestParam int staff,
                                         @RequestParam int location,
                                         @RequestParam int comfort,
                                         @RequestParam int facilities,
                                         @RequestParam int cleanliness,
                                         @RequestParam int security,
                                         @RequestParam int nbAvis,
                                         @RequestParam double moyenne) {
        Hebergement existingHebergement = hebergementRep.findById(id).orElseThrow(() -> new RuntimeException("Hebergement not found"));
        
        existingHebergement.setLocation(location);
        existingHebergement.setComfort(comfort);
        existingHebergement.setFacilities(facilities);
        existingHebergement.setCleanliness(cleanliness);
        
        if (existingHebergement.getCategorie().getIdCat() != 1) {
            existingHebergement.setSecurity(security);
        }
        
        if (existingHebergement.getCategorie().getIdCat() == 1) {
            existingHebergement.setStaff(staff);
        }
        
        existingHebergement.setMoyenne(moyenne);
        existingHebergement.setNbAvis(nbAvis);  // Mettre à jour le nombre d'avis
        
        hebergementRep.save(existingHebergement);
    }

    @PostMapping("/save")
    public Hebergement createHebergement(@RequestParam Long categorie,@RequestParam Long person,@RequestBody Hebergement hebergement) {


        hebergement.setCategorie(categorieRep.findById(categorie).get());
        hebergement.setPerson(personRep.findById(person).get());

        return hebergementService.createHebergement(hebergement);

    }
    @GetMapping("/calculateDiscountedPrice")
    public double calculateDiscountedPrice(@RequestParam double price, @RequestParam int discount) {
        double discountedPrice = price - (price * discount / 100);
        return discountedPrice;
    }

    @PutMapping("/update/{id}")
    public Hebergement updateHebergement(@PathVariable Long id ,@RequestBody Hebergement hebergement) {

        return hebergementService.updateHebergement(id,hebergement);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHebergement(@PathVariable("id") Long id) {
        Hebergement h =hebergementRep.findById(id).get();

        List<Equipement> equipements = h.getEquipements();
        equipements.clear();
        h.getChambres().forEach(room -> room.getEquipements().clear());


        hebergementRep.save(h);
        hebergementService.deleteHebergement(id);
    }
    @GetMapping ("/hebergements/{idCat}")
    public List<Hebergement> getHebergementsByCatId(@PathVariable("idCat") Long idCat) {
        return hebergementService.findByCategorieIdCat(idCat);
    }
    @GetMapping ("/get/{nom}")
    public Hebergement getHebergementsByNom(@PathVariable("nom") String nom) {
        return hebergementService.getHebergementByNom(nom);
    }
    @GetMapping ("/getId/{nom}")
    public Long getHebergement_idByNom(@PathVariable String nom) {
        return hebergementService.gethebergement_idByNom(nom);
    }
    @GetMapping ("/getByPerson/{id}")
    public List<Hebergement> findHebergementsByPerson(@PathVariable("id") Long id) {
        return hebergementService.findHebergementsByPerson(id);
    }
    @GetMapping ("/{id}")
    public Hebergement getHebergementById(@PathVariable("id") Long id) {
        return hebergementService.getHebergementById(id);
    }
    @GetMapping("/search")
    public List<Hebergement> searchHebergements(@RequestParam String terme) {

       return hebergementRep.findByVille(terme);
    }
    @GetMapping("/findByCategorieVille")
    public List<Hebergement> searchHebergements(@RequestParam String ville, @RequestParam long id) {
        Categorie categorie = categorieRep.findById(id).get();
        List<Hebergement> hebergements = hebergementRep.findByVilleOrPaysAndCategorie(ville, categorie);

        for (Hebergement hebergement : hebergements) {

            double prixEnEuro = currencyConversionService.convertToEuro(hebergement.getPrix(), hebergement.getCurrency());
            hebergement.setPrix(prixEnEuro);
            hebergement.setCurrency("EUR");


            for (Chambre chambre : hebergement.getChambres()) {
                double prixChambreEnEuro = currencyConversionService.convertToEuro(chambre.getPrix(), hebergement.getCurrency());
                chambre.setPrix(prixChambreEnEuro);
            }
        }

        return hebergements;
    }
    @PutMapping("/video")
    public Hebergement ajouterVideo(@RequestParam("video_id") long video_id, @RequestParam("hebergement_id") long hebergement_id){
   ;

        Video v=videoRep.findById(video_id).get();
        Hebergement h=hebergementRep.findById(hebergement_id).get();
        h.setVideo(v);
        hebergementRep.save(h);
        return h;
    }
    @DeleteMapping("/deleteVideo")
    public void deleteVideo(@RequestParam long hebergement){
        Hebergement h=this.hebergementRep.findById(hebergement).get();
        h.setVideo(null);
        hebergementRep.save(h);
    }
    @GetMapping("/equipements")
    public List<Equipement> getEquipements(@RequestParam long hebergement){
        Hebergement h=hebergementRep.findById(hebergement).get();
        List<Equipement> equipements=h.getEquipements();
        return equipements;
    }
    @GetMapping("/getcategorie")
    public Categorie getCategorie(@RequestParam long id){
        return this.hebergementRep.findCategorieByHebergementId(id);
    }
    @GetMapping("/liked-hebergements")
    public List<Hebergement> getLikedHebergements(@RequestParam Long userId) {
        User u=userRep.findById(userId).get();
        return hebergementService.findHebergementsLikedByUser(u);
    }

    @GetMapping("/reserved-hebergements")
    public List<Hebergement> getReservedHebergements(@RequestParam Long userId) {
        User u=userRep.findById(userId).get();
        return hebergementService.findHebergementsReservedByUser(u);
    }
    @GetMapping("/number")
    public long getTotalhebergements() {
        return hebergementRep.countBy();
    }
    @GetMapping("/{hebergementId}/categories")
    public List<CategorieEquipement> getCategoriesOfEquipementsByHebergementId(@PathVariable Long hebergementId) {
        return hebergementRep.findCategoriesOfEquipementsByHebergementId(hebergementId);
    }
    @GetMapping("/{hebergementId}/languages")
    public List<language> getLanguagesByHebergementId(@PathVariable Long hebergementId) {
        return hebergementRep.findLanguagesByHebergementId(hebergementId);
    }
    @GetMapping("/from-recommandation/{recommandationId}")
    public Hebergement getHebergementFromRecommandation(@PathVariable Long recommandationId) {
        Hebergement hebergement = hebergementService.getHebergementFromRecommandation(recommandationId);


        hebergement.setPrix(currencyConversionService.convertToEuro(hebergement.getPrix(), hebergement.getCurrency()));
        hebergement.setCurrency("EUR");


        if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
            hebergement.getChambres().forEach(chambre -> {
                chambre.setPrix(currencyConversionService.convertToEuro(chambre.getPrix(), hebergement.getCurrency()));

            });
        }

        return hebergement;
    }
    @GetMapping("/from-recommandationByCurrency/{recommandationId}")
    public Hebergement getHebergementFromRecommandation(@PathVariable Long recommandationId, @RequestParam String targetCurrency) {
        Hebergement hebergement = hebergementService.getHebergementFromRecommandation(recommandationId);


        hebergement.setPrix(currencyConversionService.convertPrice(hebergement.getPrix(), hebergement.getCurrency(), targetCurrency));
        hebergement.setCurrency(targetCurrency);


        if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
            hebergement.getChambres().forEach(chambre -> {
                chambre.setPrix(currencyConversionService.convertPrice(chambre.getPrix(), hebergement.getCurrency(), targetCurrency));

            });
        }

        return hebergement;
    }

    @GetMapping("/hasImages/{id}")
    public boolean hasImages(@PathVariable("id")long id){
        return this.hebergementRep.hasImages(id);
    }
    @GetMapping("/hasEquipements/{id}")
    public boolean hasEquipements(@PathVariable("id")long id){
        return this.hebergementRep.hasEquipments(id);
    }
    @GetMapping("/hasLocation/{id}")
    public boolean hasLocation(@PathVariable("id")long id){
        return this.hebergementRep.hasLocation(id);

    }
    @GetMapping("/hasRooms/{id}")
    public boolean hasRooms(@PathVariable("id")long id){
        return this.hebergementRep.hasRooms(id);
    }
    @PostMapping("/FreeCancellation")
    public List<Hebergement> findByFreeCancellation(@RequestBody List<Hebergement> hebergements){
        return this.hebergementRep.findHebergementsWithFreeCancellation(hebergements);
    }
    @PostMapping("/OverallAverage")
    public List<Hebergement> findHebergementsByOverallAverage(
            @RequestBody List<Hebergement> hebergements,
            @RequestParam("overallAverage") double overallAverage,
            @RequestParam("upperBound") double upperBound
    ) {
        System.out.println("Received request with overallAverage: " + overallAverage + " and upperBound: " + upperBound);
        System.out.println("Hebergements: " + hebergements);
        List<Hebergement> result = hebergementRep.findHebergementsByOverallAverage(hebergements, overallAverage, upperBound);
        System.out.println("Result: " + result);
        return result;
    }
    @PostMapping("/other/OverallAverage")
    public List<Hebergement> findotherHebergementsByOverallAverage(
            @RequestBody List<Hebergement> hebergements,
            @RequestParam("overallAverage") double overallAverage,
            @RequestParam("upperBound") double upperBound
    ) {
        System.out.println("Received request with overallAverage: " + overallAverage + " and upperBound: " + upperBound);
        System.out.println("Hebergements: " + hebergements);
        List<Hebergement> result = hebergementRep.findOtherHebergementsByOverallAverage(hebergements, overallAverage, upperBound);
        System.out.println("Result: " + result);
        return result;
    }
    @PostMapping("/nbEtoiles")
    public List<Hebergement> findHebergementsByNbEtoile(
            @RequestBody List<Hebergement> hebergements,
            @RequestParam("nbEtoile") String nbEtoile
    ) {
        return hebergementRep.findHebergementsByNbEtoiles(hebergements, Integer.parseInt(nbEtoile));
    }
    @PostMapping("/work")
    public List<Hebergement> findHebergementsByWork(@RequestBody List<Hebergement> hebergements){
        System.out.println("Received request with hebergements: " + hebergements);
        List<Hebergement> result = this.hebergementRep.findHebergementsWithWorkEquipements(hebergements);
        System.out.println("Result: " + result);
        return result;
    }
    @PostMapping("/prix")
    public List<Hebergement> findHebergementsByPrixMaxPrixMin(@RequestBody RangeRequest rangeRequest) {
        List<Hebergement> hebergements = rangeRequest.getHebergements();
        double minPrice = rangeRequest.getMinPrice();
        double maxPrice = rangeRequest.getMaxPrice();

        System.out.println("Hebergements:");
        List<Hebergement> filteredHebergements = new ArrayList<>();

        for (Hebergement hebergement : hebergements) {
            System.out.println("Hebergement: " + hebergement.getNom() + ", Prix: " + hebergement.getPrix());

            // Vérifiez si le prix de l'hébergement est entre minPrice et maxPrice
            if (hebergement.getPrix() >= minPrice && hebergement.getPrix() <= maxPrice) {
                filteredHebergements.add(hebergement);
            }
        }

        System.out.println("PRICE RANGE*******************************************************");
        System.out.println(minPrice);
        System.out.println(maxPrice);
        System.out.println("VERIFICATION******************************************************");
        System.out.println(filteredHebergements);

        return filteredHebergements;
    }

    @PostMapping("/prixChambre")
    public List<Hebergement> findHebergementsByChambrePrixMaxPrixMin(@RequestBody RangeRequest priceRangeRequest) {
        List<Hebergement> hebergements = priceRangeRequest.getHebergements();
        double minPrice = priceRangeRequest.getMinPrice();
        double maxPrice = priceRangeRequest.getMaxPrice();
        System.out.println( priceRangeRequest.getMinPrice());
        System.out.println(priceRangeRequest.getMaxPrice());
        System.out.println("Hebergements:");
        List<Hebergement> filteredHebergements = new ArrayList<>();

        for (Hebergement hebergement : hebergements) {
            System.out.println("Hebergement: " + hebergement.getNom());

            if (hebergement.getChambres() != null) {
                System.out.println("Chambres: " + hebergement.getChambres());
                boolean hasChambreInRange = hebergement.getChambres().stream()
                        .anyMatch(chambre -> chambre.getPrix() >= minPrice && chambre.getPrix() <= maxPrice);

                if (hasChambreInRange) {
                    filteredHebergements.add(hebergement);
                }
            } else {
                System.out.println("no chambres ");
            }
        }



        return filteredHebergements;
    }


    private double roundToZeroDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    @PostMapping("/minPrice")
    public double findMinPriceChambre(@RequestParam long id, @RequestParam String targetCurrency) {
        Hebergement hebergement = hebergementRep.findById(id).orElseThrow(() -> new RuntimeException("Hebergement not found"));
        double minPrice = hebergementRep.findMinChambrePriceByHebergement(hebergement);

        System.out.println("Min price before conversion: " + minPrice);

        double conversionRate = currencyConversionService.getConversionRate(hebergement.getCurrency(), targetCurrency);
        System.out.println("Conversion rate from " + hebergement.getCurrency() + " to " + targetCurrency + ": " + conversionRate);

        double convertedMinPrice = minPrice * conversionRate;
        System.out.println("Min price after conversion: " + convertedMinPrice);

        double roundedMinPrice = roundToZeroDecimalPlaces(convertedMinPrice);
        System.out.println("Rounded min price: " + roundedMinPrice);

        return roundedMinPrice;
    }
    @PostMapping("/NbChambre")
    public List<Hebergement> findHebergementsByNbChambres(@RequestBody List<Hebergement> hebergements ,@RequestParam int nbChambre){
        return this .hebergementRep.findHebergementsByNbChambres(hebergements,nbChambre);
    }
    @PostMapping("sortBynbEtoileAsc")
    public List<Hebergement> sortHebergementsByNbEtoilesAsc(@RequestBody List<Hebergement> hebergements){
       return  this.hebergementRep.sortHebergementsByNbEtoilesAsc(hebergements);
    }
    @PostMapping("sortBynbEtoileDesc")
    public List<Hebergement> sortHebergementsByNbEtoilesDesc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortHebergementsByNbEtoilesDesc(hebergements);
    }
    @PostMapping("sortByMoyenneAsc")
    public List<Hebergement> sortHebergementsByMoyenneAsc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortHebergementsByOverallAverageAsc(hebergements);
    }
    @PostMapping("/other/sortByMoyenneAsc")
    public List<Hebergement> sortOtherHebergementsByMoyenneAsc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortOtherHebergementsByOverallAverageAsc(hebergements);
    }
    @PostMapping("sortByMoyenneDesc")
    public List<Hebergement> sortHebergementsByMoyenneDesc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortHebergementsByOverallAverageDesc(hebergements);
    }
    @PostMapping("/other/sortByMoyenneDesc")
    public List<Hebergement> sortOtherHebergementsByMoyenneDesc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortOtherHebergementsByOverallAverageDesc(hebergements);
    }
    @PostMapping("sortByPrixAsc")
    public List<Hebergement> sortHebergementsByPrixAsc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortHebergementsByPrixAsc(hebergements);
    }
    @PostMapping("sortByPrixChambresAsc")
    public List<Hebergement> sortHebergementsByPrixChambreAsc(@RequestBody List<Hebergement> hebergements){
        return  this.hebergementRep.sortHebergementsByMinChambrePriceAsc(hebergements);
    }
    @PostMapping("/moyenne")
    public double findMoyenne(@RequestBody Hebergement  hebergement){
       return this.hebergementRep.findOverallAverageByHebergement(hebergement);
    }
    @PostMapping("/other/moyenne")
    public double findMoyenneOther(@RequestBody Hebergement  hebergement){
        return this.hebergementRep.findOverallAverageByOtherHebergement(hebergement);
    }


    @PostMapping("/convertPrices")
    public List<Hebergement> convertHebergementPrices(@RequestBody ConvertPricesRequest request) {
        String targetCurrency = request.getTargetCurrency();
        List<Hebergement> hebergements = request.getHebergements();

        System.out.println("***************************************************************************");
        System.out.println(targetCurrency);
        System.out.println("Received Hebergements: " + hebergements);

        List<Hebergement> convertedHebergements = new ArrayList<>();

        for (Hebergement hebergement : hebergements) {
            double conversionRate = currencyConversionService.getConversionRate(hebergement.getCurrency(), targetCurrency);
            hebergement.setPrix(hebergement.getPrix() * conversionRate);
            hebergement.setCurrency(targetCurrency);

            if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
                for (Chambre chambre : hebergement.getChambres()) {
                    chambre.setPrix(chambre.getPrix() * conversionRate);
                }
            }

            convertedHebergements.add(hebergement);
        }

        return convertedHebergements;
    }
    @PostMapping("/convertPriceRange")
    public Map<String, Double> convertPriceRange(@RequestBody PriceRangeRequest request) {
        System.out.println("**************************************************helloworld*******************************");
        double conversionRate = currencyConversionService.getConversionRate(request.getSourceCurrency(), request.getTargetCurrency());
        double convertedMinPrice = request.getMinPrice() * conversionRate;
        double convertedMaxPrice = request.getMaxPrice() * conversionRate;

        Map<String, Double> convertedPrices = new HashMap<>();
        convertedPrices.put("convertedMinPrice", convertedMinPrice);
        convertedPrices.put("convertedMaxPrice", convertedMaxPrice);

        return convertedPrices;
    }

    @GetMapping("/new-currency")
    public List<Hebergement> searchHebergementsbyNewCurrency(@RequestParam String ville, @RequestParam long id, @RequestParam String new_currency) {
        Categorie categorie = categorieRep.findById(id).orElseThrow(() -> new RuntimeException("Categorie not found"));
        List<Hebergement> hebergements = hebergementRep.findByVilleOrPaysAndCategorie(ville, categorie);

        for (Hebergement hebergement : hebergements) {
            double conversionRate = currencyConversionService.getConversionRate(hebergement.getCurrency(), new_currency);
            double prixDansNouvelleDevise = hebergement.getPrix() * conversionRate;
            double prixArrondiDansNouvelleDevise = roundToZeroDecimalPlaces(prixDansNouvelleDevise);
            hebergement.setPrix(prixArrondiDansNouvelleDevise);
            hebergement.setCurrency(new_currency);

            if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
                for (Chambre chambre : hebergement.getChambres()) {
                    double prixChambreDansNouvelleDevise = chambre.getPrix() * conversionRate;
                    double prixChambreArrondiDansNouvelleDevise = roundToZeroDecimalPlaces(prixChambreDansNouvelleDevise);
                    chambre.setPrix(prixChambreArrondiDansNouvelleDevise);
                }
            }
        }

        return hebergements;
    }
@GetMapping("/topHebergement")
public List<Hebergement> getTop10Hebergements() {
    List<Hebergement> hebergements = hebergementRep.findTopByAvisPositiveAndReservations(PageRequest.of(0, 10));

    hebergements.forEach(hebergement -> {

        hebergement.setPrix(currencyConversionService.convertToEuro(hebergement.getPrix(), hebergement.getCurrency()));
        hebergement.setCurrency("EUR");


        if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
            hebergement.getChambres().forEach(chambre -> {
                chambre.setPrix(currencyConversionService.convertToEuro(chambre.getPrix(), hebergement.getCurrency()));

            });
        }
    });

    return hebergements;
}
    @GetMapping("/topHebergementByCurrency")
    public List<Hebergement> getTop10HebergementsByCurrency(@RequestBody String targetCurrency) {
        List<Hebergement> hebergements = hebergementRep.findTopByAvisPositiveAndReservations(PageRequest.of(0, 10));

        hebergements.forEach(hebergement -> {

            hebergement.setPrix(currencyConversionService.convertPrice(hebergement.getPrix(), hebergement.getCurrency(), targetCurrency));
            hebergement.setCurrency(targetCurrency);


            if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
                hebergement.getChambres().forEach(chambre -> {
                    chambre.setPrix(currencyConversionService.convertPrice(chambre.getPrix(), hebergement.getCurrency(), targetCurrency));

                });
            }
        });

        return hebergements;
    }
    @GetMapping("/withOffers")
    public List<Hebergement> getHebergementsWithOffers(@RequestParam  Long userId) {

        List<String> likedCities = hebergementRep.findLikedHebergementCitiesByUser(userId);
        List<String> reservedCities = hebergementRep.findReservedHebergementCitiesByUser(userId);
        List<String> searchedCities = hebergementRep.findSearchedHebergementCitiesByUser(userId);


        Set<String> uniqueCities = new HashSet<>();
        uniqueCities.addAll(likedCities);
        uniqueCities.addAll(reservedCities);
        uniqueCities.addAll(searchedCities);


        List<Hebergement> hebergementsWithOffers = hebergementRep.findHebergementsWithOffersByCities(new ArrayList<>(uniqueCities));


        hebergementsWithOffers.forEach(hebergement -> {
            hebergement.setPrix(currencyConversionService.convertToEuro(hebergement.getPrix(), hebergement.getCurrency()));
            hebergement.setCurrency("EUR");

            if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
                hebergement.getChambres().forEach(chambre -> {
                    chambre.setPrix(currencyConversionService.convertToEuro(chambre.getPrix(), hebergement.getCurrency()));

                });
            }
        });

        return hebergementsWithOffers;
    }
    @GetMapping("/withOffersByCurrency")
    public List<Hebergement> getHebergementsWithOffersByCurrency(@RequestParam Long userId, @RequestParam String targetCurrency) {
        List<String> likedCities = hebergementRep.findLikedHebergementCitiesByUser(userId);
        List<String> reservedCities = hebergementRep.findReservedHebergementCitiesByUser(userId);
        List<String> searchedCities = hebergementRep.findSearchedHebergementCitiesByUser(userId);

        Set<String> uniqueCities = new HashSet<>();
        uniqueCities.addAll(likedCities);
        uniqueCities.addAll(reservedCities);
        uniqueCities.addAll(searchedCities);

        List<Hebergement> hebergementsWithOffers = hebergementRep.findHebergementsWithOffersByCities(new ArrayList<>(uniqueCities));

        hebergementsWithOffers.forEach(hebergement -> {
            hebergement.setPrix(currencyConversionService.convertPrice(hebergement.getPrix(), hebergement.getCurrency(), targetCurrency));
            hebergement.setCurrency(targetCurrency);

            if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
                hebergement.getChambres().forEach(chambre -> {
                    chambre.setPrix(currencyConversionService.convertPrice(chambre.getPrix(), hebergement.getCurrency(), targetCurrency));

                });
            }
        });

        return hebergementsWithOffers;
    }
    @GetMapping("/withoutAvis")
    public List<Hebergement> findHebergementsWithoutAvisByUser(@RequestParam long id){
        User user=userRep.findById(id).get();
        System.out.println("************************************");
        System.out.println(hebergementRep.findReservedHebergementsWithoutAvisByUser(user));
        System.out.println("************************************");
        return hebergementRep.findReservedHebergementsWithoutAvisByUser(user);
    }
    @PostMapping("/nearby-hebergements")
    public ResponseEntity<List<Hebergement>> getNearbyHebergements(@RequestParam double latitude, @RequestParam double longitude) {
        List<Hebergement> hebergements = hebergementService.findNearbyHebergements(latitude, longitude);
        return ResponseEntity.ok(hebergements);
    }
    @GetMapping("/offre/{hebergementId}")
    public List<OffreHebergement> getOffersByHebergementId(@PathVariable Long hebergementId) {
        return hebergementRep.findOffersByHebergementId(hebergementId);
    }
    @PostMapping("/currency/convertHebergementPrices")
    public Hebergement convertHebergementPrices(@RequestBody Hebergement hebergement,
                                                @RequestParam String targetCurrency) {
        double conversionRate = currencyConversionService.getConversionRate(hebergement.getCurrency(), targetCurrency);
        hebergement.setPrix(hebergement.getPrix() * conversionRate);
        hebergement.setCurrency(targetCurrency);

        if (hebergement.getChambres() != null && !hebergement.getChambres().isEmpty()) {
            for (Chambre chambre : hebergement.getChambres()) {
                chambre.setPrix(chambre.getPrix() * conversionRate);
            }
        }

        return hebergement;
    }
    @GetMapping("/currency/convert")
    public double convertPrice(@RequestParam double price,
                               @RequestParam String sourceCurrency,
                               @RequestParam String targetCurrency) {
        return currencyConversionService.convertPrice(price, sourceCurrency, targetCurrency);
    }
    @GetMapping("/convertToTND")
    public double convertPriceToTND(@RequestParam double amount, @RequestParam String currency) {
        return currencyConversionService.convertToTunisianDinar(amount, currency);
    }
}
