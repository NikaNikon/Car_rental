package com.litovchenko.carsapp.model;

import javax.persistence.*;

import java.io.Serializable;

import static com.litovchenko.carsapp.model.Constants.USERS;
import static com.litovchenko.carsapp.model.Constants.USER_ROLE_ID;

@Entity
@Table(name = USERS)
public class User implements Identified, Serializable {

    @Id
    @GeneratedValue
    private int id;

    private int roleId;

    private String login;

    private String password;

    private String email;

    private boolean blocked;

    @Transient
    private String role;

    @Transient
    private PassportData passportData;

    public User() {
    }

    public User(int id, int roleId, String login, String password, String email, boolean blocked) {
        this.id = id;
        this.roleId = roleId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.blocked = blocked;
    }


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PassportData getPassportData() {
        return passportData;
    }

    public void setPassportData(PassportData passportData) {
        this.passportData = passportData;
    }

    @Override
    public String toString() {
        StringBuilder user = new StringBuilder();
        String line_sep = System.lineSeparator();
        user.append("Login: " + login + line_sep).append("Role: " + role + line_sep);
        if (passportData != null) {
            user.append("First name: " + passportData.getFirstName() + line_sep).
                    append("Middle name: " + passportData.getMiddleName() + line_sep).
                    append("Last name: " + passportData.getLastName() + line_sep).
                    append("Date of birth: " + passportData.getDateOfBirth() + line_sep).
                    append("Phone number: " + passportData.getPhone() + line_sep);
        }
        if (blocked == true) {
            user.append("! ->> Blocked <<- !");
        }
        return user.toString();
    }
}
