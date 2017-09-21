package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.RepairmentCheckDAO;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChecksService {

    static final Logger LOGGER = Logger.getLogger(ChecksService.class);

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
        boolean isInserted = false;
        try {
            isInserted = dao.insert(check);
        } catch (SQLException e) {
            LOGGER.error("Cannot add new repairment check to database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return isInserted;
    }

    public static RepairmentCheck getByOrderId(int orderId) {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        RepairmentCheck check = null;
        try {
            check = dao.getByOrderId(orderId);
        } catch (SQLException e) {
            LOGGER.error("Cannot get order by id(" + orderId + ") from database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return check;
    }

    public static List<RepairmentCheck> getAll() {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        List<RepairmentCheck> list = null;
        try {
            list = dao.getAll();
        } catch (SQLException e) {
            LOGGER.error("Cannot get repairment checks from database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return list;
    }

    public static List<RepairmentCheck> getByUserId(int userId) {
        DAOFactory factory = new MySqlDAOFactory();
        RepairmentCheckDAO dao = factory.getRepairmentCheckDAO();
        List<RepairmentCheck> list = null;
        try {
            list = dao.getByUserId(userId);
        } catch (SQLException e) {
            LOGGER.error("Cannot get checks from database: " + e);
            throw new ApplicationException(e);
        }
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
        RepairmentCheck check = null;
        try {
            check = dao.getById(checkId);
        } catch (SQLException e) {
            LOGGER.error("Cannot get repairment check by id(" + checkId + ") from database: " + e);
            throw new ApplicationException(e);
        }
        check.setStatus(RepairmentCheck.Status.PAYED);
        boolean ifUpdated = false;
        try {
            ifUpdated = dao.update(check);
        } catch (SQLException e) {
            LOGGER.error("Cannot update repairment check in database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return ifUpdated;
    }

}
