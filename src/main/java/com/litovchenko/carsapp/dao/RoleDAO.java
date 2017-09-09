package com.litovchenko.carsapp.dao;

import com.litovchenko.carsapp.model.Role;

public interface RoleDAO extends GenericDAO<Role> {

    Role getByRoleName(String roleName);

}
