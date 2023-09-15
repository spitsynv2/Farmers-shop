package org.example.products.seed;

import java.util.Date;

public class CerealDressedSeeds extends CerealSeeds implements IDressedSeeds {
    private int processingPercentage;
    private String chemicalType;

    public CerealDressedSeeds(Date expirationData, String germinationRequirements, String cerealType, boolean fodder,int processingPercentage) {
        super(expirationData, germinationRequirements,cerealType,fodder);
        this.processingPercentage=processingPercentage;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice();
    }

    public String getChemicalType() {
        return chemicalType;
    }

    public int getProcessingPercentage() {
        return processingPercentage;
    }

    @Override
    public void setChemicalType(String chemicalType) {
        this.chemicalType=chemicalType;
    }

    @Override
    public String toString() {
        return super.toString() + " CerealDressedSeeds{" +
                "processingPercentage=" + processingPercentage +
                ", chemicalType='" + chemicalType + '\'' +
                '}';
    }
}
