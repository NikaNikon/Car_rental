package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User>{

    /**
     * Methods return users with initialized role name and passport data
     */

    User getByLogin(String login, String password);

    List<User> getByRole(String role);

    List<User> getDeptors();

    boolean updateBlockedStatus(boolean isBlocked, int id);

    boolean insertWithRole(User user);

}
