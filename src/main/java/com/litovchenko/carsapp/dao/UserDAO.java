package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDAO extends GenericDAO<User>{

    /**
     * Methods return users with initialized role name and passport data
     */

    User getByLogin(String login, String password) throws SQLException;

    List<User> getByRole(String role) throws SQLException;

    List<User> getDeptors() throws SQLException;

    boolean updateBlockedStatus(boolean isBlocked, int id) throws SQLException;

    boolean insertWithRole(User user) throws SQLException;

    Map<User, Double> getUsersWithMoneySpent() throws SQLException;

}
