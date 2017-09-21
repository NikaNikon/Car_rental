package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.RepairmentCheck;

import java.sql.SQLException;
import java.util.List;

public interface RepairmentCheckDAO extends GenericDAO<RepairmentCheck>{

    List<RepairmentCheck> getByUserId(int id) throws SQLException;

    List<RepairmentCheck> getByCarId(int id) throws SQLException;

    List<RepairmentCheck> getByStatus(RepairmentCheck.Status status) throws SQLException;

    RepairmentCheck getByOrderId(int orderId) throws SQLException;
}
