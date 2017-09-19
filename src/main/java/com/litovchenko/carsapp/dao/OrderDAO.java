package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order> {

    /**
     * All methods return orders with initialized status name, user login and car name fields
     */

    List<Order> getByUserId(int user_id);

    List<Order> getByCarId(int car_id);

    List<Order> getByStatus(String status);

    boolean deleteAllByStatus(String status);

    boolean updateStatus(int id, int statusId);

    boolean updateStatusWithComment(int id, int statusId, String comment);
}
