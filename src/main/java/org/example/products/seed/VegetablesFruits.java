package org.example.products.seed;

import java.util.Date;

public class VegetablesFruits extends Seed {
    private String type;

    public VegetablesFruits(Date expirationData, String germinationRequirements) {
        super(expirationData, germinationRequirements);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice();
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + " VegetablesFruits{" +
                "type='" + type + '\'' +
                '}';
    }
}
