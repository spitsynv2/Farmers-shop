package org.example.shopSystem;

public class Address {
    private String location;
    private String postalCode;
    public Address(String postalCode,String location){
        this.location = location;
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "location='" + location + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
