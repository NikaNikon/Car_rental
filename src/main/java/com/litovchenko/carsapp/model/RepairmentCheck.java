package com.litovchenko.carsapp.model;

import javax.persistence.*;
import java.sql.Date;

import static com.litovchenko.carsapp.model.Constants.CAR_ID;
import static com.litovchenko.carsapp.model.Constants.REPAIRMENT_CHECKS;
import static com.litovchenko.carsapp.model.Constants.USER_ID;

@Entity
@Table(name = REPAIRMENT_CHECKS)
public class RepairmentCheck  implements Identified{

    @Id
    @GeneratedValue
    private int id;

    private int orderId;

    @Column(name = USER_ID)
    private int userId;

    @Column(name = CAR_ID)
    private int carId;

    private Date date;

    private double price;

    private String comment;

    private Status status;

    @Transient
    private String carName;

    @Transient
    private String userLogin;

    public RepairmentCheck() {
    }

    public RepairmentCheck(int id, int orderId, int userId, int carId, Date date,
                           double price, String comment, Status status) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.carId = carId;
        this.date = date;
        this.price = price;
        this.comment = comment;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public enum Status {
        UNPAYED, PAYED
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String toString() {
        StringBuilder check = new StringBuilder();
        String line_sep = System.lineSeparator();
        check.append("Repairment check #" + id + line_sep).append("User: " + userLogin + line_sep).
                append("Car: " + carName + "(" + carId + ")" + line_sep).
                append("__________________________________________" + line_sep).
                append("Price: " + price + line_sep).append(date + line_sep);
        if (comment != null) {
            check.append("Comment: " + comment + line_sep);
        }
        check.append(status+ line_sep);
        return check.toString();
    }
}
