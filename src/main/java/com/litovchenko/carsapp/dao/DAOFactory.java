package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.mongoDb.MongoDBDAOFactory;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

public abstract class DAOFactory implements AutoCloseable {

    public static final int MY_SQL = 1;
    public static final int MONGODB = 2;

    public static DAOFactory getDAOFactory(int factory){
        switch(factory){
            case MY_SQL:
                return new MySqlDAOFactory();
            case MONGODB:
                return new MongoDBDAOFactory();
            default:
                throw new IllegalArgumentException("No such factory");
        }
    }

    public abstract void startTransaction();
    public abstract void abortTransaction();

    public abstract CarClassDAO getCarClassDAO();
    public abstract CarDAO getCarDAO();
    public abstract OrderDAO getOrderDAO();
    public abstract PassportDataDAO getPassportDataDAO();
    public abstract RepairmentCheckDAO getRepairmentCheckDAO();
    public abstract UserDAO getUserDAO();
    public abstract StatusDAO getStatusDAO();
    public abstract RoleDAO getRoleDAO();

}
