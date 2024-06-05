package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConvertPricesRequest {
    @JsonProperty("hebergements")
    private List<Hebergement> hebergements;

    @JsonProperty("targetCurrency")
    private String targetCurrency;

    // Getters and Setters

    public List<Hebergement> getHebergements() {
        return hebergements;
    }

    public void setHebergements(List<Hebergement> hebergements) {
        this.hebergements = hebergements;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}