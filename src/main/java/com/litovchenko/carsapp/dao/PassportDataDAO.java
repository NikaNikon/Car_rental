package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.PassportData;

import java.sql.SQLException;

public interface PassportDataDAO extends GenericDAO<PassportData> {

    PassportData getByUserLogin(String login) throws SQLException;

}
