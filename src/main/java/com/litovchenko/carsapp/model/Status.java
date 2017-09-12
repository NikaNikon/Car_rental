package com.litovchenko.carsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.litovchenko.carsapp.model.Constants.STATUSES;

@Entity
@Table(name = STATUSES)
public class Status implements Identified {

    @Id
    @GeneratedValue
    private int id;

    private String status;

    public Status(){};

    public Status(int id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
