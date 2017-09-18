package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.OrderDAO;
import com.litovchenko.carsapp.dao.PassportDataDAO;
import com.litovchenko.carsapp.dao.RepairmentCheckDAO;
import com.litovchenko.carsapp.model.Order;
import com.litovchenko.carsapp.model.PassportData;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
                    closeFactory(factory);
                    return "you have unpayed repairment check";
                }
            }
        }
        List<Order> orders = dao.getByUserId(user.getId());
        if(orders != null) {
            for (Order order : orders) {
                String status = order.getStatus();
                if (status.equals("NEW") || status.equals("PENDING") || status.equals("PAYED")) {
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
        PassportData passport = new PassportData(userId, firstName, middleName, lastName, birthDate, phone);
        Order order = new Order(0, userId, carId, startDate, endDate,
                orderDate, driver, 0, 0);

        factory.startTransaction();
        boolean isPassportDataInserted = passportDao.insert(passport);
        boolean isOrderInserted = dao.insert(order);
        System.out.println(isPassportDataInserted + "           " + isOrderInserted);
        if(isPassportDataInserted == false || isOrderInserted == false){
            factory.abortTransaction();
            closeFactory(factory);
            return false;
        } else {
            closeFactory(factory);
            return true;
        }
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

}
