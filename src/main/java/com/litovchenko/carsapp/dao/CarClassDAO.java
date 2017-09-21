package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.CarClass;

import java.sql.SQLException;

public interface CarClassDAO extends GenericDAO<CarClass>{

    boolean removeByClassName(String class_name) throws SQLException;

}
