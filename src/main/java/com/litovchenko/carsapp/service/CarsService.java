package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.CarDAO;
import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.util.*;

public class CarsService {

    private static void closeFactory(DAOFactory factory){
        try {
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getModels(){
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        List<String> list;
        list = dao.getModels();
        closeFactory(factory);
        return list;
    }

    public static List<Car> getAvailable(){
        List<Car> list;
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        list = dao.getByStatus(Car.Status.AVAILABLE);
        closeFactory(factory);
        return list;
    }

    public static List<Car> getList(){
        List<Car> list;
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        list = dao.getAll();
        closeFactory(factory);
        return list;
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

    public static boolean deleteById(String deleteAction){
        int id = Integer.parseInt(deleteAction.split("_")[1]);
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        boolean isDeleted = dao.deleteById(id);
        closeFactory(factory);
        return isDeleted;
    }

    public static boolean addNewCar(String model, String carClass, String price, String fullName,
                                    String description, String driverPrice){
        Car car = new Car(0, model, Integer.parseInt(carClass), Double.parseDouble(price),
                fullName, description, Car.Status.AVAILABLE, Double.parseDouble(driverPrice));
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        boolean isInserted = dao.insert(car);
        closeFactory(factory);
        return isInserted;
    }

    public static Car getById(String id){
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        Car car = dao.getById(Integer.parseInt(id));
        closeFactory(factory);
        return car;
    }

    public static boolean update(int id, String model, String carClass, String price, String fullName,
                                 String description, Car.Status status, String driverPrice){
        Car car = new Car(id, model, Integer.parseInt(carClass), Double.parseDouble(price),
                fullName, description, status, Double.parseDouble(driverPrice));
        DAOFactory factory= new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        boolean isUpdated = dao.update(car);
        closeFactory(factory);
        return isUpdated;
    }

    public static List<Object> getStatuses(){
        List<Object> list = Arrays.asList(Car.Status.values());
        return list;
    }

    public static boolean changeStatus(Car car, Car.Status newStatus){
        car.setStatus(newStatus);
        DAOFactory factory = new MySqlDAOFactory();
        CarDAO dao = factory.getCarDAO();
        if(!dao.getById(car.getId()).getStatus().equals(Car.Status.AVAILABLE)){
            closeFactory(factory);
            return false;
        }
        boolean ifUpdated = dao.update(car);
        closeFactory(factory);
        return ifUpdated;
    }

}
