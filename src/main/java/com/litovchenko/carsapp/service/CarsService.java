package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.CarDAO;
import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CarsService {

    private static final DAOFactory factory = new MySqlDAOFactory();
    private static final CarDAO dao = factory.getCarDAO();

    public static List<String> getModels(){
        List<String> list;
        list = dao.getModels();
        return list;
    }

    public static List<Car> getList(){
        return dao.getAll();
    }

    public static List<Car> getByClass(String carClass){
        return dao.getByClass(carClass);
    }

    public static List<Car> pickByPrice(List<Car> list, double price, String minMax){
        List<Car> newList = new ArrayList<>();
        switch (minMax){
            case "min" : {
                for(Car car: list){
                    if(car.getPrice() >= price){
                        newList.add(car);
                    }
                }
                break;
            }
            case "max" : {
                for(Car car: list){
                    if(car.getPrice() <= price){
                        newList.add(car);
                    }
                }
                break;
            }
        }
        System.out.println(newList);
        return newList;
    }

    public static List<Car> pickByModel(List<Car> list, String model){
        List<Car> newList = new ArrayList<>();
        for(Car car : list){
            if(model.equals(car.getModel())){
                newList.add(car);
            }
        }
        return newList;
    }

    public static List<Car> pickByClass(List<Car> list, String model){
        List<Car> newList = new ArrayList<>();
        for(Car car: list){
            if(model.equals(car.getCarClassName())){
                newList.add(car);
            }
        }
        return newList;
    }

    public static List<Car> sort(List<Car>list, String sorting){
        switch(sorting){
            case "min-max": {
                Collections.sort(list, new Comparator<Car>() {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if(o1.getPrice() == o2.getPrice()){
                            return 0;
                        }
                        return o1.getPrice() > o2.getPrice() ? 1 : -1;
                    }
                });
                break;
            }
            case "max-min": {
                Collections.sort(list, new Comparator<Car>() {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if(o1.getPrice() == o2.getPrice()){
                            return 0;
                        }
                        return o1.getPrice() < o2.getPrice() ? 1 : -1;
                    }
                });
                break;
            }
            case "alph": {
                Collections.sort(list, new Comparator<Car>() {
                    @Override
                    public int compare(Car o1, Car o2) {
                        return o1.getFullName().compareToIgnoreCase(o2.getFullName());
                    }
                });
                break;
            }
        }
        return list;
    }
}
