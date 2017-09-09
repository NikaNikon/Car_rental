package com.litovchenko.carsapp.mongoDb;

import com.litovchenko.carsapp.dao.*;

public class MongoDBDAOFactory extends DAOFactory {
    @Override
    public void startTransaction() {

    }

    @Override
    public void abortTransaction() {

    }

    @Override
    public CarClassDAO getCarClassDAO() {
        return null;
    }

    @Override
    public CarDAO getCarDAO() {
        return null;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return null;
    }

    @Override
    public PassportDataDAO getPassportDataDAO() {
        return null;
    }

    @Override
    public RepairmentCheckDAO getRepairmentCheckDAO() {
        return null;
    }

    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public StatusDAO getStatusDAO() {
        return null;
    }

    @Override
    public RoleDAO getRoleDAO() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
