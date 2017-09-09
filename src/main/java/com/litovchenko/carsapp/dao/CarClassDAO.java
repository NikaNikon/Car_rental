package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.CarClass;

public interface CarClassDAO extends GenericDAO<CarClass>{

    boolean removeByClassName(String class_name);

}
