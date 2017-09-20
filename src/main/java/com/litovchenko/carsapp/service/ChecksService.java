package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.RepairmentCheckDAO;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChecksService {

    private static void closeFactory(DAOFactory factory) {
        try {
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean saveCheck(int orderId, double price, String comment) {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        int userId = OrdersService.getOrder(orderId).getUserId();
        RepairmentCheck check = new RepairmentCheck(0, orderId, userId,
                OrdersService.getOrder(orderId).getCarId(),
                new Date(Calendar.getInstance().getTime().getTime()), price,
                comment, RepairmentCheck.Status.UNPAYED);
        boolean isInserted = dao.insert(check);
        closeFactory(factory);
        return isInserted;
    }

    public static RepairmentCheck getByOrderId(int orderId) {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        RepairmentCheck check = dao.getByOrderId(orderId);
        closeFactory(factory);
        return check;
    }

    public static List<RepairmentCheck> getAll() {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        List<RepairmentCheck> list = dao.getAll();
        closeFactory(factory);
        return list;
    }

    public static List<RepairmentCheck> getByUserId(int userId) {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        List<RepairmentCheck> list = dao.getByUserId(userId);
        closeFactory(factory);
        return list;
    }

    public static List<RepairmentCheck> getUnpayed(List<RepairmentCheck> list) {
        if (list == null) {
            return null;
        }
        List<RepairmentCheck> result = new ArrayList<>();
        for (RepairmentCheck check : list) {
            if (check.getStatus().equals(RepairmentCheck.Status.UNPAYED)) {
                result.add(check);
            }
        }
        if (result.isEmpty()) {
            result = null;
        }
        return result;
    }

    public static boolean pay(int checkId) {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        RepairmentCheck check = dao.getById(checkId);
        check.setStatus(RepairmentCheck.Status.PAYED);
        boolean ifUpdated = dao.update(check);
        closeFactory(factory);
        return ifUpdated;
    }

}
