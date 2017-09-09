package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Identified;

import java.util.List;

public interface GenericDAO<T extends Identified> {

    T getById(Integer id);

    boolean insert(T object);

    boolean update(T object);

    boolean deleteById(int id);

    List<T> getAll();

}
