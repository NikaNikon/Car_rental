package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.UserDAO;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

public class UsersServise {

    private static final String LINE_SEP = System.lineSeparator();

    public static User logIn(String login, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = dao.getByLogin(login, password);
        try {
            factory.close();
        } catch (Exception e) {
            System.out.println("Can't close DAOFactory" + LINE_SEP + e);
        }
        return user;
    }

    public static User addUser(String login, String email, String password) {
        DAOFactory factory = new MySqlDAOFactory();
        UserDAO dao = factory.getUserDAO();
        User user = new User(0, 0, login, password, email, false);
        if(!dao.insert(user)){
            user = null;
        }
        try {
            factory.close();
        } catch (Exception e) {
            System.out.println("Can't close DAOFactory" + LINE_SEP + e);
        }
        return user;
    }

}
