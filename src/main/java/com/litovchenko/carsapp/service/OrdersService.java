package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.OrderDAO;
import com.litovchenko.carsapp.dao.RepairmentCheckDAO;
import com.litovchenko.carsapp.model.Order;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.util.List;

public class OrdersService {

    private static void closeFactory(DAOFactory factory){
        try {
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String checkAbilityToMakeOrders(User user){

        if(user.isBlocked()){
            return "you are blocked.";
        }

        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        RepairmentCheckDAO checkDAO = factory.getRepairmentCheckDAO();

        List<RepairmentCheck> list = checkDAO.getByUserId(user.getId());
        if(list != null) {
            for (RepairmentCheck check : list) {
                if (check.getStatus().equals(RepairmentCheck.Status.UNPAYED)) {
                    return "you have unpayed repairment check";
                }
            }
        }
        List<Order> orders = dao.getByUserId(user.getId());
        if(orders != null) {
            for (Order order : orders) {
                String status = order.getStatus();
                if (status.equals("NEW") || status.equals("PENDING") || status.equals("PAYED")) {
                    return "you have opened order";
                }
            }
        }
        return "OK";
    }

}
