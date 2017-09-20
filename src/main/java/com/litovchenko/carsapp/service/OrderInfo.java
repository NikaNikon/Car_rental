package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.model.Order;
import com.litovchenko.carsapp.model.User;

public class OrderInfo {
    private Order order;
    private Car car;
    private User user;

    private String status;

    public OrderInfo(Order order, Car car, User user, String status) {
        this.order = order;
        this.car = car;
        this.user = user;
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
