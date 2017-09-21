package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Car;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO  extends GenericDAO<Car>{

    /**
     * Methods return cars with initialized car name field
     */

    List<Car> getByModel(String model) throws SQLException;

    List<Car> getByClass(String car_class) throws SQLException;

    List<Car> getByPrice(int min, int max) throws SQLException;

    List<Car> getByStatus(Car.Status status) throws SQLException;

    List<String> getModels() throws SQLException;

    boolean deleteAllByClass(String car_class) throws SQLException;

}
