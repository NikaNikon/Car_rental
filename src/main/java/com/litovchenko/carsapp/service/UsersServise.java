package com.litovchenko.carsapp.service;

import com.litovchenko.carsapp.dao.DAOFactory;
import com.litovchenko.carsapp.dao.UserDAO;
import com.litovchenko.carsapp.model.User;
import com.litovchenko.carsapp.mySql.MySqlDAOFactory;

public class UsersServise {

    public static final DAOFactory factory = new MySqlDAOFactory();
    public static final UserDAO dao = factory.getUserDAO();

    public static User logIn(String login, String password){
        User user = dao.getByLogin(login, password);
        return user;
    }

}
