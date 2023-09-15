package org.example.products;

public abstract class Product {
    private int Id;
    private String information;

    protected String name;
    private double price;

    public double getTotalPrice(){
        return price;
    }
    public double getPrice() {
        return price;
    }
    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public void setId(int id) {
        Id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setInformation(String information) {
        this.information = information;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", information='" + information + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
