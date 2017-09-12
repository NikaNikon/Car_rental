package com.litovchenko.carsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.litovchenko.carsapp.model.Constants.ROLES;

@Entity
@Table(name = ROLES)
public class Role implements Identified {

    @Id
    @GeneratedValue
    private int id;

    private String roleName;

    public Role(){};

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
