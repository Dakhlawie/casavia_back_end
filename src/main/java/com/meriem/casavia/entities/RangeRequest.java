package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RangeRequest {
    @JsonProperty("hebergements")
    private List<Hebergement> hebergements;

    @JsonProperty("minPrice")
    private double minPrice;

    @JsonProperty("maxPrice")
    private double maxPrice;

    // Getters and Setters

    public List<Hebergement> getHebergements() {
        return hebergements;
    }

    public void setHebergements(List<Hebergement> hebergements) {
        this.hebergements = hebergements;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
