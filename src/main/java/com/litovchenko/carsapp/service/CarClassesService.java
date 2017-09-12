package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.CarClassDAO;
import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.model.CarClass;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.util.ArrayList;
import java.util.List;

public class CarClassesService {

    public static List<String> getClasses(){
        DAOFactory factory = new MySqlDAOFactory();
        CarClassDAO dao = factory.getCarClassDAO();
        List<CarClass> list = dao.getAll();
        List<String> result = new ArrayList<>();
        for(CarClass carClass: list){
            result.add(carClass.getCarClassName());
        }
        return result;
    }
}
