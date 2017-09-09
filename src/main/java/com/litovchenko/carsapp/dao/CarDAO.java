package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Car;

import java.util.List;

public interface CarDAO  extends GenericDAO<Car>{

    /**
     * Methods return cars with initialized car name field
     */

    List<Car> getByModel(String model);

    List<Car> getByClass(String car_class);

    List<Car> getByPrice(int min, int max);

    List<Car> getByStatus(Car.Status status);

    boolean deleteAllByClass(String car_class);

}
