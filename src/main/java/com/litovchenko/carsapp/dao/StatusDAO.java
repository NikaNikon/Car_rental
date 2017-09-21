package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Status;

import java.sql.SQLException;

public interface StatusDAO extends GenericDAO<Status> {

    Status getByName(String statusName) throws SQLException;

}
