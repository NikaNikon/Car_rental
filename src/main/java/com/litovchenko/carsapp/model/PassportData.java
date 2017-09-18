package com.litovchenko.carsapp.model;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static com.litovchenko.carsapp.model.Constants.*;

@Entity
@Table(name = PASSPORT_DATA)
public class PassportData implements Identified {

    private int userId;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date dateOfBirth;

    @Transient
    private String birthday;

    private String phone;

    private PassportData() {
    }

    public PassportData(int userId, String firstName, String middleName,
                        String lastName, Date dateOfBirth, String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        birthday = sdf.format(dateOfBirth);

    }


    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public void setId(Integer id) {
        this.userId = id;
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
