package com.litovchenko.carsapp.model;

import javax.persistence.*;
import java.sql.Date;

import static com.litovchenko.carsapp.model.Constants.*;

@Entity
@Table(name = PASSPORT_DATA)
public class PassportData implements Identified {

    @Id
    private int id;

    private String passportCode;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date dateOfBirth;

    private String phone;

    private PassportData() {
    }

    public PassportData(int userId, String passportCode, String firstName, String middleName,
                        String lastName, Date dateOfBirth, String phone) {
        id = userId;
        this.passportCode = passportCode;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
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

    public String getPassportCode() {
        return passportCode;
    }

    public void setPassportCode(String passportCode) {
        this.passportCode = passportCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
