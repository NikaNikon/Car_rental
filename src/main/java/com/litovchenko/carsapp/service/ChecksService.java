package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.RepairmentCheckDAO;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.sql.Date;
import java.util.Calendar;

public class ChecksService {

    private static void closeFactory(DAOFactory factory){
        try {
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean saveCheck(int userId, int orderId, double price, String comment){
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        RepairmentCheck check = new RepairmentCheck(0, orderId, userId,
                OrdersService.getOrder(orderId).getCarId(),
                new Date(Calendar.getInstance().getTime().getTime()), price,
                comment, RepairmentCheck.Status.UNPAYED);
        boolean isInserted = dao.insert(check);
        closeFactory(factory);
        System.out.println(isInserted);
        return isInserted;
    }

    public static RepairmentCheck getByOrderId(int orderId){
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        RepairmentCheck check = dao.getByOrderId(orderId);
        closeFactory(factory);
        return check;
    }

}
