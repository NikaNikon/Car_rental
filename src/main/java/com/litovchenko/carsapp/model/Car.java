package com.litovchenko.carsapp.model;

import javax.persistence.*;

import static com.litovchenko.carsapp.model.Constants.*;

@Entity
@Table(name = CARS)
public class Car implements Identified {

    @Id
    @GeneratedValue
    private int id;

    private String model;

    private int carClassId;

    private double price;

    private String fullName;

    private String description;

    private Status status;

    private double driverPrice;

    @Transient
    private String carClassName;

    public enum Status {
        AVAILABLE, IN_RENT, BEING_REPAIRED
    }

    public Car() {
    }

    public Car(String model, int class_id, double price, String full_name,
               String description, Status status, double driver_price) {
        this.model = model;
        this.carClassId = class_id;
        this.price = price;
        this.fullName = full_name;
        this.description = description;
        this.status = status;
        this.driverPrice = driver_price;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCarClassId() {
        return carClassId;
    }

    public void setCarClassId(int carClassId) {
        this.carClassId = carClassId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getDriverPrice() {
        return driverPrice;
    }

    public void setDriverPrice(double driverPrice) {
        this.driverPrice = driverPrice;
    }

    public String getCarClassName() {
        return carClassName;
    }

    public void setCarClassName(String carClassName) {
        this.carClassName = carClassName;
    }

    @Override
    public String toString() {
        StringBuilder car = new StringBuilder();
        String line_sep = System.lineSeparator();
        car.append(fullName + line_sep);
        if (carClassName != null) {
            car.append("Class: " + carClassName + line_sep);
        }
        if (description != null) {
            car.append(description);
        }
        car.append("Price: " + price + " per day" + line_sep).
                append("Driver price: " + driverPrice + " per day" + line_sep);
        return car.toString();
    }
}
