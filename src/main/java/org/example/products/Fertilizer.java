package org.example.products;

import java.util.Date;

public class Fertilizer extends Product{
    private Date expirationDate;
    private String type;

    @Override
    public double getTotalPrice() {
        return this.getPrice();
    }

    public String getType() {
        return type;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString()+  " Fertilizer{" +
                "expirationDate=" + expirationDate +
                ", type='" + type + '\'' +
                '}';
    }
}
