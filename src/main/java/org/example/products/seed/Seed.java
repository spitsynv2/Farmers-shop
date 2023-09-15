package org.example.products.seed;

import org.example.products.Product;

import java.util.Date;

public abstract class Seed extends Product {
    private Date expirationData;
    private String germinationRequirements;

    public Seed(Date expirationData, String germinationRequirements){
        this.expirationData=expirationData;
        this.germinationRequirements=germinationRequirements;
    }

    @Override
    public double getTotalPrice() {
        return getPrice();
    }

    public String getGerminationRequirements() {
        return germinationRequirements;
    }

    @Override
    public String toString() {
        return super.toString() + " Seed{" +
                "expirationData=" + expirationData +
                ", germinationRequirements='" + germinationRequirements + '\'' +
                '}';
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    public Date getExpirationData() {
        return expirationData;
    }
}
