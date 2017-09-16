package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory {

    private static DataSource dataSource;
    private Connection connection;

    static int count = 0;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/mysql");
        } catch (NamingException e) {
            throw new IllegalStateException("Missin JNDI", e);
        }
    }

    public MySqlDAOFactory() {
        connection = getConnection();
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            if (dataSource == null) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/car_rental_db?user=root&password=ybrfybrfybrf");
                return connection;
            }
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Cannot connect to database" + System.lineSeparator() + e);
        }
        return connection;
    }

    @Override
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Cannot start transaction" + System.lineSeparator() + e);
        }
    }

    @Override
    public void abortTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Cannot abort transaction" + System.lineSeparator() + e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Cannot close MySqlDAOFactory" + System.lineSeparator() + e);
        }
    }

    @Override
    public CarClassDAO getCarClassDAO() {
        return new MySqlCarClassDAO(connection);
    }

    @Override
    public CarDAO getCarDAO() {
        return new MySqlCarDAO(connection);
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new MySqlOrderDAO(connection);
    }

    @Override
    public PassportDataDAO getPassportDataDAO() {
        return new MySqlPassportDataDAO(connection);
    }

    @Override
    public RepairmentCheckDAO getRepairmentCheckDAO() {
        return new MySqlRepairmentCheckDAO(connection);
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySqlUserDAO(connection);
    }

    @Override
    public StatusDAO getStatusDAO() {
        return new MySqlStatusDAO(connection);
    }

    @Override
    public RoleDAO getRoleDAO(){
        return new MySqlRoleDAO(connection);
    }
}
