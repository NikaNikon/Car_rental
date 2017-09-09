package com.litovchenko.carsapp.model;

import javax.persistence.*;

import static com.litovchenko.carsapp.model.Constants.CAR_CLASSES;

@Entity
@Table(name = CAR_CLASSES)
public class CarClass implements Identified {

    @Id
    @GeneratedValue
    private int id;

    private String carClassName;

    public CarClass(int id, String class_name) {
        this.id = id;
        this.carClassName = class_name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarClassName() {
        return carClassName;
    }

    public void setCarClassName(String carClassName) {
        this.carClassName = carClassName;
    }
}
