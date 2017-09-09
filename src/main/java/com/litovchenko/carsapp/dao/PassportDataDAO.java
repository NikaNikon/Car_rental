package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.PassportData;

public interface PassportDataDAO extends GenericDAO<PassportData> {

    PassportData getByCode(String code);

    PassportData getByUserLogin(String login);

}
