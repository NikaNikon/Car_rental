package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Role;

import java.sql.SQLException;

public interface RoleDAO extends GenericDAO<Role> {

    Role getByRoleName(String roleName) throws SQLException;

}
