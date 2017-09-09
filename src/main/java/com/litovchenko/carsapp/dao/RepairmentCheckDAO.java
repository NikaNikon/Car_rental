package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.RepairmentCheck;

import java.util.List;

public interface RepairmentCheckDAO extends GenericDAO<RepairmentCheck>{

    List<RepairmentCheck> getByUserId(int id);

    List<RepairmentCheck> getByCarId(int id);

    List<RepairmentCheck> getByStatus(RepairmentCheck.Status status);

}
