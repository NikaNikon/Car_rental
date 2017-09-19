package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.UserDAO;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

import java.util.List;

public class UsersServise {

    private static void closeDaoFactory(DAOFactory factory){
        try {
            factory.close();
        } catch (Exception e) {
            System.out.println("Can't close DAOFactory" + System.lineSeparator() + e);
        }
    }

    public static User logIn(String login, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = dao.getByLogin(login, password);
        closeDaoFactory(factory);
        return user;
    }

    public static User addUser(String login, String email, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = new User(0, 0, login, password, email, false);
        if(!dao.insert(user)){
            user = null;
        }
        closeDaoFactory(factory);
        return user;
    }

    public static List<User> getManagers(){
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        List<User> list = dao.getByRole("MANAGER");
        closeDaoFactory(factory);
        return list;
    }

    public static List<User> getCustomers(){
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        List<User> list = dao.getByRole("CUSTOMER");
        closeDaoFactory(factory);
        return list;
    }

    public static boolean changeBlockedStatus(boolean blockedStatus, int id){
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        boolean isUpdated = dao.updateBlockedStatus(blockedStatus, id);
        closeDaoFactory(factory);
        return isUpdated;
    }

    public static boolean deleteUser(int id){
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        boolean isDeleted = dao.deleteById(id);
        closeDaoFactory(factory);
        return isDeleted;
    }

    public static boolean addManager(String login, String email, String password){
        DAOFactory factory = new MySqlDAOFactory();
        int roleId = factory.getRoleDAO().getByRoleName("MANAGER").getId();
        UserDAO dao = factory.getUserDAO();
        boolean isInserted = dao.insertWithRole(new User(0,roleId, login, password, email, false));
        closeDaoFactory(factory);
        return isInserted;
    }

    public static User getUser(int id){
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = dao.getById(id);
        closeDaoFactory(factory);
        return user;
    }

}
