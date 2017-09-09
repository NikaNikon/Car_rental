package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Status;

public interface StatusDAO extends GenericDAO<Status> {

    Status getByName(String statusName);

}
