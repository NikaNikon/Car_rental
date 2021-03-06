package com.litovchenko.carsapp.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.litovchenko.carsapp.dao.*;
import com.litovchenko.carsapp.model.Order;
import com.litovchenko.carsapp.model.PassportData;
import com.litovchenko.carsapp.model.RepairmentCheck;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdersService {

    static final Logger LOGGER = Logger.getLogger(OrdersService.class);

    private static void closeFactory(DAOFactory factory) {
        try {
            factory.close();
        } catch (Exception e) {
            LOGGER.error("Cannot close database connection: " + e);
            throw new ApplicationException(e);
        }
    }

    public static String checkAbilityToMakeOrders(User user) {

        if (user.isBlocked()) {
            return "you are blocked.";
        }

        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();

        List<RepairmentCheck> list = ChecksService.getByUserId(user.getId());
        if (list != null) {
            for (RepairmentCheck check : list) {
                if (check.getStatus().equals(RepairmentCheck.Status.UNPAYED)) {
                    closeFactory(factory);
                    return "you have unpayed repairment check";
                }
            }
        }
        List<Order> orders = null;
        try {
            orders = dao.getByUserId(user.getId());
        } catch (SQLException e) {
            LOGGER.error("Cannot get orders by user id: " + e);
            throw new ApplicationException(e);
        }
        if (orders != null) {
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
                                           String phone, Date start, Date end) {
        if (firstName == null || middleName == null || lastName == null || birth == null || phone == null ||
                start == null || end == null) {
            return false;
        }
        String namePattern = "([A-Z][a-z]+)";
        if (!firstName.matches(namePattern)) {
            return false;
        }
        if (!middleName.matches(namePattern)) {
            return false;
        }
        if (!lastName.matches(namePattern)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (birth.after(sdf.parse(getMaxDateOfBirth())) ||
                    start.before(sdf.parse(getDaysAfterToday(1))) ||
                    start.after(sdf.parse(getDaysAfterToday(3))) ||
                    end.before(start) ||
                    start.equals(end)) {
                return false;
            }
        } catch (ParseException e) {
            LOGGER.error("Cannot resolve input date in order form: " + e);
            throw new UserInputException(e);
        }

        if (!phone.matches("(\\+380.{9})")) {
            return false;
        }
        return true;
    }

    /*public static boolean makeOrder(String firstName, String middleName, String lastName, Date birth,
                                    String phone, int userId, int carId, Date start, Date end, boolean driver) {
        if (!isOrderDataValid(firstName, middleName, lastName, birth, phone, start, end)) {
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
        try {
            if (passportDao.getById(userId) != null) {
                if (dao.insert(order)) {
                    success = true;
                }
            } else {
                PassportData passport = new PassportData(userId, firstName, middleName,
                        lastName, birthDate, phone);
                factory.startTransaction();
                boolean isPassportDataInserted = passportDao.insert(passport);
                boolean isOrderInserted = dao.insert(order);
                if (isPassportDataInserted == false || isOrderInserted == false) {
                    success = false;
                    factory.abortTransaction();
                } else {
                    success = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot process data to make order: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return success;
    }*/


    public static Order makeOrder(String firstName, String middleName, String lastName, Date birth,
                                    String phone, int userId, int carId, Date start, Date end, boolean driver) {
        if (!isOrderDataValid(firstName, middleName, lastName, birth, phone, start, end)) {
            return null;
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
        try {
            if (passportDao.getById(userId) != null) {
                if (dao.insert(order)) {
                    success = true;
                }
            } else {
                PassportData passport = new PassportData(userId, firstName, middleName,
                        lastName, birthDate, phone);
                factory.startTransaction();
                boolean isPassportDataInserted = passportDao.insert(passport);
                boolean isOrderInserted = dao.insert(order);
                if (isPassportDataInserted == false || isOrderInserted == false) {
                    order = null;
                    factory.abortTransaction();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot process data to make order: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return order;
    }


    public static String getMaxDateOfBirth() {
        Date now = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.YEAR, -18);
        Date maxDateOfBirth = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(maxDateOfBirth);
    }

    public static String getDaysAfterToday(int days) {
        Date now = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, days);
        Date maxStartDate = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(maxStartDate);
    }

    public static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<OrderInfo> getOrders(int userId) {
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO orderDAO = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        CarDAO carDAO = factory.getCarDAO();
        UserDAO userDAO = factory.getUserDAO();
        List<Order> orders;
        if (userId < 0) {
            try {
                orders = orderDAO.getAll();
            } catch (SQLException e) {
                LOGGER.error("Cannot get orders from database: " + e);
                throw new ApplicationException(e);
            }
        } else {
            try {
                orders = orderDAO.getByUserId(userId);
            } catch (SQLException e) {
                LOGGER.error("Cannot get orders by user id from database: " + e);
                throw new ApplicationException(e);
            }
        }
        if (orders == null) {
            closeFactory(factory);
            return null;
        }
        List<OrderInfo> list = new ArrayList<>();
        for (Order order : orders) {
            OrderInfo orderInfo = null;
            try {
                orderInfo = new OrderInfo(order, carDAO.getById(order.getCarId()),
                        userDAO.getById(order.getUserId()),
                        statusDAO.getById(order.getStatusId()).getStatus());
            } catch (SQLException e) {
                LOGGER.error("Cannot get information from database to create orderInfo: " + e);
                throw new ApplicationException(e);
            }
            list.add(orderInfo);
        }
        closeFactory(factory);
        return list;
    }

    public static List<OrderInfo> getOrders() {
        return getOrders(-1);
    }

    public static List<OrderInfo> getByStatus(List<OrderInfo> orders, String status) {
        if (orders == null || status == null) {
            return null;
        }
        List<OrderInfo> list = new ArrayList<>();
        for (OrderInfo info : orders) {
            if (status.equals(info.getStatus())) {
                list.add(info);
            }
        }
        if (list.isEmpty()) {
            list = null;
        }
        return list;
    }

    public static List<OrderInfo> getActiveOrders(List<OrderInfo> orders) {
        return getByStatus(orders, "CONFIRMED");
    }

    public static List<OrderInfo> getClosedOrders(List<OrderInfo> orders) {
        return getByStatus(orders, "CLOSED");
    }

    public static List<OrderInfo> getNewOrders(List<OrderInfo> orders) {
        return getByStatus(orders, "NEW");
    }

    private static boolean changeStatus(int id, String status) {
        DAOFactory factory = new MySqlDAOFactory();
        StatusDAO statusDAO = factory.getStatusDAO();
        OrderDAO dao = factory.getOrderDAO();
        boolean isUpdated = false;
        try {
            isUpdated = dao.updateStatus(id, statusDAO.getByName(status).getId());
        } catch (SQLException e) {
            LOGGER.error("Cannot get user status by name from database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return isUpdated;
    }

    public static boolean confirm(int id) {
        return changeStatus(id, "CONFIRMED");
    }

    public static boolean reject(int id, String comment) {
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        StatusDAO statusDAO = factory.getStatusDAO();
        boolean isUpdated = false;
        try {
            isUpdated = dao.updateStatusWithComment(id,
                    statusDAO.getByName("REJECTED").getId(), comment);
        } catch (SQLException e) {
            LOGGER.error("Cannot get user status by name from database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return isUpdated;
    }

    public static boolean closeOrder(int id) {

        return changeStatus(id, "CLOSED");
    }

    public static Order getOrder(int id) {
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        Order order = null;
        try {
            order = dao.getById(id);
        } catch (SQLException e) {
            LOGGER.error("Cannot get order by id from database: " + e);
            throw new ApplicationException(e);
        }
        closeFactory(factory);
        return order;
    }

    public static boolean checkAbility(int orderId) {
        if (ChecksService.getByOrderId(orderId) != null) {
            return false;
        }
        return true;
    }

    public static byte[] getCheck(int orderId) {
        DAOFactory factory = new MySqlDAOFactory();
        OrderDAO dao = factory.getOrderDAO();
        Order order = null;
        try {
            order = dao.getById(orderId);
        } catch (SQLException e) {
            LOGGER.error("Cannot get order by id from database: " + e);
            throw new ApplicationException(e);
        }
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
        File file = new File(orderId + "orderCheck.pdf");
        file.delete();
        try {
            PdfWriter.getInstance(doc, byteArrayOutputStream);
            doc.open();
            doc.add(new Paragraph(order.toString()));
            doc.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            LOGGER.error("Cannot process document to generate a check" + e);
            throw new ApplicationException(e);
        }
        file.delete();

        return bytes;
    }

}

