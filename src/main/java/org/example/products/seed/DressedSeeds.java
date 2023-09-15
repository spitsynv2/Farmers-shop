package org.example.products.seed;

import java.util.Date;

public class DressedSeeds extends Seed {
    private String chemicalType;

    public DressedSeeds(Date expirationData, String germinationRequirements) {
        super(expirationData, germinationRequirements);
    }

    public void setChemicalType(String chemicalType) {
        this.chemicalType = chemicalType;
    }

    @Override
    public double getTotalPrice() {
        return getPrice();
    }

    public String getChemicalType() {
        return chemicalType;
    }

    @Override
    public String toString() {
        return super.toString() + " DressedSeeds{" +
                "chemicalType='" + chemicalType + '\'' +
                '}';
    }
}
