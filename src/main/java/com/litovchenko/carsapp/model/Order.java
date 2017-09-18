package com.litovchenko.carsapp.model;

import javax.persistence.*;
import java.sql.Date;

import static com.litovchenko.carsapp.model.Constants.*;

@Entity
@Table(name = ORDERS)
public class Order implements Identified {

    @Id
    @GeneratedValue
    private int id;

    private int userId;

    private int carId;

    private Date startDate;

    private Date endDate;

    private Date orderDate;

    private boolean driver;

    private double totalPrice;

    private int statusId;

    private String managerComment;

    @Transient
    private String status;

    @Transient
    private String userLogin;

    @Transient
    private String carName;

    public Order() {
    }

    public Order(int id, int user_id, int car_id, Date start_date, Date end_date, Date order_date,
                 boolean driver, double total_price, int status_id) {
        this.id = id;
        this.userId = user_id;
        this.carId = car_id;
        this.startDate = start_date;
        this.endDate = end_date;
        this.orderDate = order_date;
        this.driver = driver;
        this.totalPrice = total_price;
        this.statusId = status_id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    @Override
    public String toString() {
        StringBuilder order = new StringBuilder();
        String line_sep = System.lineSeparator();
        order.append("Order_id: " + id + line_sep);
        if (userLogin != null) {
            order.append("User: " + userLogin + line_sep);
        } else {
            order.append("User id: " + userId + line_sep);
        }
        if (carName != null) {
            order.append("Car: " + carName + line_sep);
        } else {
            order.append("Car id: " + carId + line_sep);
        }
        order.append("Start date: " + startDate + line_sep).
                append("End date: " + endDate + line_sep).append("Driver: ");
        if (driver == true) {
            order.append("yes" + line_sep);
        } else {
            order.append("no" + line_sep);
        }
        order.append("__________________________________________" + line_sep);
        order.append("Total price: " + totalPrice + line_sep).append(orderDate + line_sep);
        if (status != null) {
            order.append("Order status: " + status);
        }
        if (managerComment != null) {
            order.append("Manager comment: " + managerComment + line_sep);
        }

        return order.toString();
    }

}
