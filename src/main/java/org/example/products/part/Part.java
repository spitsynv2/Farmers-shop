package org.example.products.part;

import org.example.products.Product;

import java.util.EnumSet;

enum PartType {Machinery, Vehicle}
public class Part extends Product {

    private String manufacturer;
    private EnumSet<PartType> partTypes = null;
    private String model;
    private String machineData;

    public void buildPart(String model, String machineData){
        this.model=model;
        this.machineData=machineData;
        partTypes = EnumSet.of(PartType.Machinery,PartType.Vehicle);
    }

    public void buildPartMachinery(String data){
        this.machineData=data;
        partTypes = EnumSet.of(PartType.Machinery);
    }

    public void buildPartVehicle(String data){
        this.model=data;
        partTypes = EnumSet.of(PartType.Vehicle);
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getMachineData() {
        return machineData;
    }

    public EnumSet<PartType> getPartTypes() {
        return partTypes;
    }

    @Override
    public String toString() {
        return super.toString()+ " TypedPart{" +
                "partTypes=" + partTypes +
                ", model='" + model + '\'' +
                ", machineData='" + machineData + '\'' +
                '}';
    }
}
