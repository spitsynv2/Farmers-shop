package org.example.products.seed;

import java.util.Date;

public class RawSeed extends Seed {
    private boolean diseaseResistant;

    public RawSeed(Date expirationData, String germinationRequirements,boolean diseaseResistant) {
        super(expirationData, germinationRequirements);
        this.diseaseResistant=diseaseResistant;
    }

    @Override
    public double getTotalPrice() {
        return getPrice();
    }


    @Override
    public String toString() {
        return super.toString() + " RawSeed{" +
                "diseaseResistant=" + diseaseResistant +
                '}';
    }
}
