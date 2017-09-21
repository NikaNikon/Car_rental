package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.UserDAO;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UsersServise {

    static final Logger LOGGER = Logger.getLogger(UsersServise.class);

    private static void closeDaoFactory(DAOFactory factory) {
        try {
            factory.close();
        } catch (Exception e) {
            LOGGER.error("Error occurred while closing database connection: " + e);
            throw new ApplicationException(e);
        }
    }

    public static User logIn(String login, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = null;
        try {
            user = dao.getByLogin(login, password);
        } catch (SQLException e) {
            LOGGER.error("Cannot get user by login and password from database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return user;
    }

    public static User addUser(String login, String email, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = new User(0, 0, login, password, email, false);
        try {
            if (!dao.insert(user)) {
                user = null;
            }
            user = dao.getById(user.getId());
        } catch (SQLException e) {
            LOGGER.error("Cannot add user to database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return user;
    }

    public static List<User> getManagers() {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        List<User> list = null;
        try {
            list = dao.getByRole("MANAGER");
        } catch (SQLException e) {
            LOGGER.error("Cannot get users by role from database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return list;
    }

    public static List<User> getCustomers() {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        List<User> list = null;
        try {
            list = dao.getByRole("CUSTOMER");
        } catch (SQLException e) {
            LOGGER.error("Cannot get users by role from database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return list;
    }

    public static boolean changeBlockedStatus(boolean blockedStatus, int id) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        boolean isUpdated = false;
        try {
            isUpdated = dao.updateBlockedStatus(blockedStatus, id);
        } catch (SQLException e) {
            LOGGER.error("Cannot block/unblock user in database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return isUpdated;
    }

    public static boolean deleteUser(int id) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        boolean isDeleted = false;
        try {
            isDeleted = dao.deleteById(id);
        } catch (SQLException e) {
            LOGGER.error("Cannot delete user by id(" + id + ") from database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return isDeleted;
    }

    public static boolean addManager(String login, String email, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        boolean isInserted = false;
        try {
            int roleId = factory.getRoleDAO().getByRoleName("MANAGER").getId();
            UserDAO dao = factory.getUserDAO();
            isInserted = dao.insertWithRole(new User(0, roleId, login, password, email, false));
        } catch (SQLException e) {
            LOGGER.error("Cannot add new manager to database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return isInserted;
    }

    public static User getUser(int id) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = null;
        try {
            user = dao.getById(id);
        } catch (SQLException e) {
            LOGGER.error("Cannot get user by id(" + id + ") from database: " + e);
            throw new ApplicationException(e);
        }
        closeDaoFactory(factory);
        return user;
    }

}
