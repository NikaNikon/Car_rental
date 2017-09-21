package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Identified;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T extends Identified> {

    T getById(Integer id) throws SQLException;

    boolean insert(T object) throws SQLException;

    boolean update(T object) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

}
