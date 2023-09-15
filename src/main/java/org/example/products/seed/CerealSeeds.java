package org.example.products.seed;

import java.util.Date;

public class CerealSeeds extends Seed {
    private String cerealType;
    private boolean fodder;
    public CerealSeeds(Date expirationData, String germinationRequirements, String cerealType, boolean fodder){
        super(expirationData,germinationRequirements);
        this.cerealType=cerealType;
        this.fodder=fodder;
    }
    @Override
    public double getTotalPrice() {
        return getPrice()*0.9;
    }

    public String getCerealType() {
        return cerealType;
    }

    public boolean isFodder() {
        return fodder;
    }

    @Override
    public String toString() {
        return super.toString() + " CerealSeeds{" +
                "cerealType='" + cerealType + '\'' +
                ", fodder=" + fodder +
                '}';
    }
}
