package org.example.products;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.example.products.part.Part;
import org.example.products.seed.*;

public class ProductFactory {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/farmers_shop";
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "user";

    public List<Product> getAllProducts(Boolean inShop, int productId_) {
        List<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            String sql;

            if (productId_ != 0){
                sql = "SELECT * FROM Product WHERE Id =" + productId_;
            } else if (inShop){
                sql = "SELECT * FROM Product, Shop_Product WHERE Product_Id = Product.Id";
            }else {
                sql = "SELECT * FROM Product";
            }

            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("Product.Id");
                    String productName = resultSet.getString("Product.name");
                    double productPrice = resultSet.getDouble("Product.price");
                    String information = resultSet.getString("Product.information");
                    String manufacturer = resultSet.getString("Product.manufacturer");
                    Date expirationDate = resultSet.getDate("Product.expiration_date");
                    String fertilizerType = resultSet.getString("Product.fertilizer_type");
                    String germination = resultSet.getString("Product.germination");
                    String cerealType = resultSet.getString("Product.cereal_type");
                    boolean foodder = resultSet.getBoolean("Product.foodder");
                    String vegfruitType = resultSet.getString("Product.vegfruit_type");
                    String chemType = resultSet.getString("Product.chem_type");
                    boolean diseaseRes = resultSet.getBoolean("Product.disease_res");
                    String vehicleModel = resultSet.getString("Product.vehcicle_model");
                    String machineData = resultSet.getString("Product.machine_data");
                    int processingPercentage = resultSet.getInt("Product.processing_percentage");

                    if (fertilizerType != null) {

                        Fertilizer fertilizer = new Fertilizer();
                        fertilizer.setId(productId);
                        fertilizer.setName(productName);
                        fertilizer.setPrice(productPrice);
                        fertilizer.setInformation(information);
                        fertilizer.setExpirationDate(expirationDate);
                        fertilizer.setType(fertilizerType);

                        products.add(fertilizer);
                    } else if (germination != null) {
                        Seed seed = null;
                        if (vegfruitType != null) {
                            VegetablesFruits vegetablesFruits = new VegetablesFruits(expirationDate, germination);
                            vegetablesFruits.setType(vegfruitType);
                            seed = vegetablesFruits;
                        } else if (diseaseRes) {
                            seed = new RawSeed(expirationDate, germination, true);
                        } else if (cerealType != null) {

                            if (chemType!=null){
                                CerealDressedSeeds cerealDressedSeeds = new CerealDressedSeeds(expirationDate,germination,cerealType,foodder,processingPercentage);
                                cerealDressedSeeds.setChemicalType(chemType);
                                seed = cerealDressedSeeds;

                            }else {
                                seed = new CerealSeeds(expirationDate, germination, cerealType, foodder);
                            }

                        } else {
                            DressedSeeds dressedSeeds = new DressedSeeds(expirationDate, germination);
                            dressedSeeds.setChemicalType(chemType);
                            seed = dressedSeeds;
                        }

                        seed.setId(productId);
                        seed.setName(productName);
                        seed.setPrice(productPrice);
                        seed.setInformation(information);
                        products.add(seed);

                    }  else if (machineData != null || vehicleModel != null) {
                        Part part = new Part();
                        part.setId(productId);
                        part.setName(productName);
                        part.setPrice(productPrice);
                        part.setInformation(information);
                        part.setManufacturer(manufacturer);


                        if (machineData != null && vehicleModel != null) {
                            part.buildPart(vehicleModel, machineData);
                        } else if (machineData != null) {
                            part.buildPartMachinery(machineData);
                        } else {
                            part.buildPartVehicle(vehicleModel);
                        }

                        products.add(part);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
