package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.*;
import com.litovchenko.carsapp.model.Order;
import com.litovchenko.carsapp.model.PassportData;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                    closeFactory(factory);
                    return "you have unpayed repairment check";
                }
            }
        }
        List<Order> orders = dao.getByUserId(user.getId());
        if(orders != null) {
            for (Order order : orders) {
                String status = order.getStatus();
                if (status.equals("NEW") || status.equals("CONFIRMED")) {
                    closeFactory(factory);
                    return "you have opened order";
                }
            }
        }
        closeFactory(factory);
        return "OK";
    }

    public static boolean isOrderDataValid(String firstName, String middleName, String lastName, Date birth,
                                           String phone, Date start, Date end){
        if(firstName == null || middleName == null || lastName == null || birth == null || phone == null ||
                start == null || end == null){
            return false;
        }
        String namePattern = "([A-Z][a-z]+)";
        if(!firstName.matches(namePattern)){
            return false;
        }
        if(!middleName.matches(namePattern)){
            return false;
        }
        if(!lastName.matches(namePattern)){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(birth.after(sdf.parse(getMaxDateOfBirth()))){
                return false;
            }
            if(start.before(sdf.parse(getDaysAfterToday(1)))){
                return false;
            }
            if(end.before(start)){
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Can't parse date" + System.lineSeparator() + e);
        }

        if(!phone.matches("(\\+380.{9})")){
            return false;
        }
        return true;
    }

    public static boolean makeOrder(String firstName, String middleName, String lastName, Date birth,
                                    String phone, int userId, int carId, Date start, Date end, boolean driver){
        if(!isOrderDataValid(firstName, middleName, lastName, birth, phone, start, end)){
            return false;
        }

        java.sql.Date birthDate = new java.sql.Date(birth.getTime());
        java.sql.Date startDate = new java.sql.Date(start.getTime());
        java.sql.Date endDate = new java.sql.Date(end.getTime());
        java.sql.Date orderDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        PassportDataDAO passportDao = factory.getPassportDataDAO();
        boolean success = false;

        Order order = new Order(0, userId, carId, startDate, endDate,
                orderDate, driver, 0, 0);
        if(passportDao.getById(userId) != null){
            if(dao.insert(order)){
                success = true;
            }
        } else {
            PassportData passport = new PassportData(userId, firstName, middleName,
                    lastName, birthDate, phone);
            factory.startTransaction();
            boolean isPassportDataInserted = passportDao.insert(passport);
            boolean isOrderInserted = dao.insert(order);
            if(isPassportDataInserted == false || isOrderInserted == false){
                success = false;
                factory.abortTransaction();
            }else {
                success = true;
            }
        }
        closeFactory(factory);
        return success;
    }

    public static String getMaxDateOfBirth(){
        Date now = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.YEAR, -18);
        Date maxDateOfBirth = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(maxDateOfBirth);
    }

    public static String getDaysAfterToday(int days){
        Date now = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, days);
        Date maxStartDate =c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(maxStartDate);
    }

    public static Date getDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<Order, String> getOrders(int userId){
        Map<Order, String> map = new HashMap<>();
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        for(Order order: dao.getByUserId(userId)){
            map.put(order, statusDAO.getById(order.getStatusId()).getStatus());
        }
        if(map.isEmpty()){
            map = null;
        }
        closeFactory(factory);
        return map;
    }

    public static Map<Order, String> getOrders(){
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        Map<Order, String> map = new HashMap<>();
        for(Order order: dao.getAll()){
            map.put(order, statusDAO.getById(order.getStatusId()).getStatus());
        }
        closeFactory(factory);
        if(map.isEmpty()){
            map = null;
        }
        return map;
    }

    public static Map<Order, String> getActiveOrders(Map<Order, String> orders){
        Map<Order, String> map = new HashMap<>();
        for(Map.Entry<Order, String> entry: orders.entrySet()){
            Order order = entry.getKey();
            if("CONFIRMED".equals(order.getStatus())){
                map.put(order, entry.getValue());
            }
        }
        if(map.isEmpty()){
            map = null;
        }
        return map;
    }

    public static Map<Order, String> getClosedOrders(Map<Order, String> orders){
        Map<Order, String> map = new HashMap<>();
        for(Map.Entry<Order, String> entry: orders.entrySet()){
            Order order = entry.getKey();
            if("CLOSED".equals(order.getStatus()) || "REJECTED".equals(order.getStatus())){
                map.put(order, entry.getValue());
            }
        }
        if(map.isEmpty()){
            map = null;
        }
        return map;
    }

    public static Map<Order, String> getNewOrders(){
        Map<Order, String> map = new HashMap<>();
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        for(Order order: dao.getByStatus("NEW")){
            map.put(order, statusDAO.getById(order.getStatusId()).getStatus());
        }
        closeFactory(factory);
        if(map.isEmpty()){
            map = null;
        }
        return map;
    }

    public static Map<Order, String> getNewOrders(int id){
        Map<Order, String> map = new HashMap<>();
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        int newStatusId = statusDAO.getByName("NEW").getId();
        for(Order order: dao.getByUserId(id)){
            if(order.getStatusId() == newStatusId) {
                map.put(order, "NEW");
            }
        }
        closeFactory(factory);
        if(map.isEmpty()){
            map = null;
        }
        return map;
    }

    private static boolean changeStatus(int id, String status){
        DAOFactory factory = new MySqlDAOFactory();
        StatusDAO statusDAO = factory.getStatusDAO();
        OrderDAO dao = factory.getOrderDAO();
        boolean isUpdated = dao.updateStatus(id, statusDAO.getByName(status).getId());
        closeFactory(factory);
        return isUpdated;
    }

    public static boolean confirm(int id){
        return changeStatus(id, "CONFIRMED");
    }

    public static boolean reject(int id, String comment){
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        boolean isUpdated = dao.updateStatusWithComment(id,
                statusDAO.getByName("REJECTED").getId(), comment);
        closeFactory(factory);
        return isUpdated;
    }

    public static boolean closeOrder(int id){
        return changeStatus(id, "CLOSED");
    }

    public static Order getOrder(int id){
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        Order order = dao.getById(id);
        closeFactory(factory);
        return order;
    }

    public static boolean checkAbility(int orderId){
        if(ChecksService.getByOrderId(orderId) != null){
            return false;
        }
        return true;
    }

}
