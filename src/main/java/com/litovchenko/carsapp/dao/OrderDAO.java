package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends GenericDAO<Order> {

    /**
     * All methods return orders with initialized status name, user login and car name fields
     */

    List<Order> getByUserId(int user_id) throws SQLException;

    List<Order> getByCarId(int car_id) throws SQLException;

    List<Order> getByStatus(String status) throws SQLException;

    boolean deleteAllByStatus(String status) throws SQLException;

    boolean updateStatus(int id, int statusId) throws SQLException;

    boolean updateStatusWithComment(int id, int statusId, String comment) throws SQLException;
}
