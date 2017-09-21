package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.CarClassDAO;
import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.model.CarClass;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarClassesService {

    static final Logger LOGGER = Logger.getLogger(CarClassesService.class);

    private static void closeFactory(DAOFactory factory) {
        try {
            factory.close();
        } catch (Exception e) {
            LOGGER.error("Error occurred while closing database connection: " + e);
            throw new ApplicationException(e);
        }
    }

    public static List<String> getClasses() {
        DAOFactory factory = new MySqlDAOFactory();
        CarClassDAO dao = factory.getCarClassDAO();
        List<CarClass> list = null;
        try {
            list = dao.getAll();
        } catch (SQLException e) {
            LOGGER.error("Cannot get car classes' names from database: " + e);
            throw new ApplicationException(e);
        }
        List<String> result = new ArrayList<>();
        for (CarClass carClass : list) {
            result.add(carClass.getCarClassName());
        }
        closeFactory(factory);
        return result;
    }

    public static List<CarClass> getAll() {
        DAOFactory factory = new MySqlDAOFactory();
        CarClassDAO dao = factory.getCarClassDAO();
        List<CarClass> list = null;
        try {
            list = dao.getAll();
        } catch (SQLException e) {
            LOGGER.error("Cannot get car classes from database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return list;
    }
}
